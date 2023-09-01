package com.project.test.securitygateway.service.impl;

import com.project.test.securitygateway.dto.UserDto;
import com.project.test.securitygateway.exception.UnauthorizedException;
import com.project.test.securitygateway.securityJwt.component.Encoder;
import com.project.test.securitygateway.securityJwt.dto.AuthRequest;
import com.project.test.securitygateway.securityJwt.dto.AuthResponse;
import com.project.test.securitygateway.securityJwt.dto.Role;
import com.project.test.securitygateway.securityJwt.dto.User;
import com.project.test.securitygateway.securityJwt.util.JWTUtil;
import com.project.test.securitygateway.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class LoginServiceImplTest {

    @InjectMocks
    private LoginServiceImpl loginService;
    @Mock
    private Encoder encoder;
    @Mock
    private JWTUtil jwtUtil;
    @Mock
    private UserService userService;

    @Test
    void login_when_return_success() {
        //given
        AuthRequest request = new AuthRequest("santtiagolozano@gmail.com",
                "12345");
        UserDto userDto = new UserDto("123", "santiago", "santtiagolozano@gmail.com",
                "asdbybc");
        //when
        when(userService.findUserByEmail(request.getUsername())).thenReturn(Mono.just(userDto));
        when(encoder.encode(request.getPassword())).thenReturn("asdbybc");
        when(jwtUtil.generateToken(any())).thenReturn("asdhabdaSDANduysbkasdakdbakd");

        //then
        StepVerifier.create(loginService.login(request))
                .assertNext(res -> {
                    assertNotNull(res.getToken());
                })
                .verifyComplete();

    }

    @Test
    void login_when_return_unauthorized_error() {
        //given
        AuthRequest request = new AuthRequest("santtiagolozano@gmail.com",
                "12345");
        UserDto userDto = new UserDto("123", "santiago", "santtiagolozano@gmail.com",
                "asdbybc");
        //when
        when(userService.findUserByEmail(request.getUsername())).thenReturn(Mono.just(userDto));
        when(encoder.encode(request.getPassword())).thenReturn("asdbybcervvv");

        //then
        StepVerifier.create(loginService.login(request))
                .expectErrorMatches(throwable -> throwable instanceof UnauthorizedException)
                .verify();

    }

}