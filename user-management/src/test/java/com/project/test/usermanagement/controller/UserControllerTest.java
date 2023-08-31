package com.project.test.usermanagement.controller;

import com.project.test.usermanagement.exception.ProjectTestException;
import com.project.test.usermanagement.dto.UserDto;
import com.project.test.usermanagement.entity.UserEntity;
import com.project.test.usermanagement.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private UserServiceImpl userServiceImpl;

    private UserDto userDto;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {

        userDto = UserDto.builder().name("Santiago Lozano")
                .email("santtiagolozano@gmail.com")
                .password("123").build();

        userEntity = UserEntity.builder()
                .id("1").email("santtiagolozano@gmail.com")
                .name("Santiago Lozano").build();
    }

    @Test
    void save_user_and_return_ok() {

        //when
        Mockito.when(userServiceImpl.saveUser(any())).thenReturn(Mono.just(userDto));

        //then
        webClient.post()
                .uri("/api/v1/user")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.email").isEqualTo(userDto.getEmail())
                .jsonPath("$.name").isEqualTo(userDto.getName())
                .returnResult();
    }


    @Test
    void save_user_and_return_error() {

        String messageError = "Ya existe un usuario con este mismo email: " + userDto.getEmail();

        //when
        Mockito.when(userServiceImpl.saveUser(any())).thenReturn(Mono.error(new ProjectTestException(HttpStatus.INTERNAL_SERVER_ERROR,
                messageError)));

        //then
        webClient.post()
                .uri("/api/v1/user")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userDto)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.code").isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .jsonPath("$.message").isEqualTo(messageError)
                .returnResult();
    }


    @Test
    void get_allUser_and_return_ok() {

        //when
        Mockito.when(userServiceImpl.findAllUser()).thenReturn(Flux.just(userDto));

        //then
        webClient.get()
                .uri("/api/v1/user")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.[0].email").isEqualTo(userDto.getEmail())
                .jsonPath("$.[0].name").isEqualTo(userDto.getName())
                .returnResult();
    }


    @Test
    void get_allUser_and_return_error() {

        //when
        Mockito.when(userServiceImpl.saveUser(any())).thenReturn(Mono.error(new ProjectTestException(HttpStatus.NOT_FOUND,
                "No existen usuarios.")));

        //then
        webClient.post()
                .uri("/api/v1/user")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userDto)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.code").isEqualTo(HttpStatus.NOT_FOUND.toString())
                .jsonPath("$.message").isEqualTo("No existen usuarios.")
                .returnResult();
    }

}
