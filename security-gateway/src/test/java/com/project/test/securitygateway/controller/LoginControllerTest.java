package com.project.test.securitygateway.controller;

import com.project.test.securitygateway.dto.UserDto;
import com.project.test.securitygateway.exception.SecurityGatewayException;
import com.project.test.securitygateway.service.impl.LoginServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = LoginController.class)
class LoginControllerTest {

    @Autowired
    private WebTestClient webClient;
    @MockBean
    private LoginServiceImpl loginService;
    @Test
    @WithMockUser
    void createUser_when_return_success() {
        //given
        UserDto request = new UserDto(null, "santiago",
                "santtiagolozano@gmail.com", "123");
        UserDto response = new UserDto("1", "santiago",
                "santtiagolozano@gmail.com", "asdadds");

        //when
        when(loginService.createUser(request)).thenReturn(Mono.just(response));

        //then
        webClient.mutateWith(SecurityMockServerConfigurers.csrf())  // Enable CSRF for this test
                .post()
                .uri("/autentication/create-user")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.email").isEqualTo(response.getEmail())
                .jsonPath("$.id").isEqualTo(response.getId())
                .jsonPath("$.email").isEqualTo(response.getEmail())
                .jsonPath("$.id").isEqualTo(response.getId())
                .returnResult();
    }

    @Test
    @WithMockUser
    void createUser_when_return_internal_error() {
        //given
        UserDto request = new UserDto(null, "santiago",
                "santtiagolozano@gmail.com", "123");
        UserDto response = new UserDto("1", "santiago",
                "santtiagolozano@gmail.com", "asdadds");

        //when
        when(loginService.createUser(request)).thenReturn(Mono.error(new SecurityGatewayException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno")));

        //then
        webClient.mutateWith(SecurityMockServerConfigurers.csrf())  // Enable CSRF for this test
                .post()
                .uri("/autentication/create-user")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    @WithMockUser
    void createUser_when_return_badRequest_error() {
        //given
        UserDto request = new UserDto();
        UserDto response = new UserDto("1", "santiago",
                "santtiagolozano@gmail.com", "asdadds");

        //when
        when(loginService.createUser(request)).thenReturn(Mono.just(response));

        //then
        webClient.mutateWith(SecurityMockServerConfigurers.csrf())  // Enable CSRF for this test
                .post()
                .uri("/autentication/create-user")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();
    }

}