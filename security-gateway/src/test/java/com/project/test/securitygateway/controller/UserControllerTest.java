package com.project.test.securitygateway.controller;

import com.project.test.securitygateway.dto.UserDto;
import com.project.test.securitygateway.exception.SecurityGatewayException;
import com.project.test.securitygateway.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private WebTestClient webClient;
    @MockBean
    private UserServiceImpl userService;


    @Test
    @WithMockUser
    void getAllUsers_when_return_success() {
        //given
        List<UserDto> response = Arrays.asList(new UserDto("1", "santiago",
                        "santtiagolozano@gmail.com", "asdadds"),
                new UserDto("2", "santiago2",
                        "santtiagolozano2@gmail.com", "asdadds2"));

        //when
        when(userService.findAllUsers()).thenReturn(Flux.fromIterable(response));

        //then
        webClient.get()
                .uri("/api/v1/user")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.[0].email").isEqualTo(response.get(0).getEmail())
                .jsonPath("$.[0].id").isEqualTo(response.get(0).getId())
                .jsonPath("$.[1].email").isEqualTo(response.get(1).getEmail())
                .jsonPath("$.[1].id").isEqualTo(response.get(1).getId())
                .returnResult();
    }

    @Test
    @WithMockUser
    void getAllUsers_when_return_internal_error() {

        //when
        when(userService.findAllUsers()).thenReturn(Flux.error(new SecurityGatewayException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno")));

        //then
        webClient.get()
                .uri("/api/v1/user")
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void getAllUsers_when_return_unauthorized_error() {
        //given
        List<UserDto> response = Arrays.asList(new UserDto("1", "santiago",
                        "santtiagolozano@gmail.com", "asdadds"),
                new UserDto("2", "santiago2",
                        "santtiagolozano2@gmail.com", "asdadds2"));

        //when
        when(userService.findAllUsers()).thenReturn(Flux.fromIterable(response));

        //then
        webClient.get()
                .uri("/api/v1/user")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @WithMockUser
    void getUserByEmail_when_return_success() {
        //given
        UserDto response = new UserDto("1", "santiago",
                "santtiagolozano@gmail.com", "asdadds");

        //when
        when(userService.findUserByEmail(response.getEmail())).thenReturn(Mono.just(response));

        //then
        webClient.get()
                .uri("/api/v1/user/" + response.getEmail())
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
    void getUserByEmail_when_return_internal_error() {
        //given
        String email = "email@gmail.com";

        //when
        when(userService.findUserByEmail(email)).thenReturn(Mono.error(new SecurityGatewayException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno")));

        //then
        webClient.get()
                .uri("/api/v1/user/" + email)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void getUserByEmail_when_return_unauthorized_error() {
        //given
        String email = "email@gmail.com";
        UserDto response = new UserDto("1", "santiago",
                "santtiagolozano@gmail.com", "asdadds");
        //when
        when(userService.findUserByEmail(email)).thenReturn(Mono.just(response));

        //then
        webClient.get()
                .uri("/api/v1/user/" + email)
                .exchange()
                .expectStatus().isUnauthorized();
    }
}