package com.project.test.securitygateway.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.test.securitygateway.dto.UserDto;
import com.project.test.securitygateway.exception.UnauthorizedException;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserRepository.class, ReactorClientHttpConnector.class}, initializers = ConfigDataApplicationContextInitializer.class)
class UserRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private ReactorClientHttpConnector connector;

    private UserRepository userRepository;

    @BeforeEach
    void initialize() {
        userRepository = new UserRepository(mockWsUri, connector);
    }


    @Test
    void getAllUsers_when_return_success() throws JsonProcessingException {
        //given
        UserDto response = new UserDto("123", "Santiago", "santiago@gmail.com", "132ddsd");

        //when
        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(response))
                .setResponseCode(HttpStatus.OK.value())
                .setHeader("Content-Type", "application/json"));

        //then
        StepVerifier.create(userRepository.getAllUsers())
                .assertNext(res -> {
                    assertNotNull(res);
                    assertEquals(res, response);
                }).verifyComplete();
    }

    @Test
    void getAllUsers_when_return_unahutorized_error() throws JsonProcessingException {
        //given
        UserDto response = new UserDto("123", "Santiago", "santiago@gmail.com", "132ddsd");

        //when
        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(response))
                .setResponseCode(HttpStatus.UNAUTHORIZED.value())
                .setHeader("Content-Type", "application/json"));

        //then
        StepVerifier.create(userRepository.getAllUsers())
                .expectErrorMatches(throwable -> throwable instanceof UnauthorizedException)
                .verify();
    }

    @Test
    void getUserByEmail_when_return_success() throws JsonProcessingException {
        //given
        String email = "santiago@gmail.com";
        UserDto response = new UserDto("123", "Santiago", "santiago@gmail.com", "132ddsd");

        //When
        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(response))
                .setResponseCode(HttpStatus.OK.value())
                .setHeader("Content-Type", "application/json"));

        //then
        StepVerifier.create(userRepository.getUserByEmail(response.getEmail()))
                .assertNext(res -> {
                    assertNotNull(res);
                    assertEquals(email, res.getEmail());
                }).verifyComplete();
    }

    @Test
    void getUserByEmail_when_return_unauthorized_error() throws JsonProcessingException {
        //given
        String email = "santiago@gmail.com";
        UserDto response = new UserDto("123", "Santiago", "santiago@gmail.com", "132ddsd");

        //When
        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(response))
                .setResponseCode(HttpStatus.UNAUTHORIZED.value())
                .setHeader("Content-Type", "application/json"));

        //then
        StepVerifier.create(userRepository.getUserByEmail(response.getEmail()))
                .expectErrorMatches(throwable -> throwable instanceof UnauthorizedException)
                .verify();
    }

    @Test
    void createUser_when_return_success() throws JsonProcessingException {
        //given
        UserDto request = new UserDto(null, "Santiago", "santiago@gmail.com", "132ddsd");
        UserDto response = new UserDto("123", "Santiago", "santiago@gmail.com", "132ddsd");

        //When
        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(response))
                .setResponseCode(HttpStatus.OK.value())
                .setHeader("Content-Type", "application/json"));

        //then
        StepVerifier.create(userRepository.createUser(request))
                .assertNext(res -> {
                    assertNotNull(res);
                    assertEquals(res.getEmail(), response.getEmail());
                    assertEquals(res.getName(), response.getName());
                }).verifyComplete();
    }

    @Test
    void createUser_when_return_unauthorized_error() throws JsonProcessingException {
        //given
        UserDto request = new UserDto(null, "Santiago", "santiago@gmail.com", "132ddsd");
        UserDto response = new UserDto("123", "Santiago", "santiago@gmail.com", "132ddsd");

        //When
        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(response))
                .setResponseCode(HttpStatus.UNAUTHORIZED.value())
                .setHeader("Content-Type", "application/json"));

        //then
        StepVerifier.create(userRepository.createUser(request))
                .expectErrorMatches(throwable -> throwable instanceof UnauthorizedException)
                .verify();
    }
}