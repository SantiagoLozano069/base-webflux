package com.project.test.securitygateway.controller;

import com.project.test.securitygateway.dto.UserDto;
import com.project.test.securitygateway.securityJwt.dto.AuthRequest;
import com.project.test.securitygateway.securityJwt.dto.AuthResponse;
import com.project.test.securitygateway.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/autentication")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public Mono<AuthResponse> login(@RequestBody AuthRequest loginRequest) {
        return loginService.login(loginRequest)
                .doOnNext(response -> log.info(loginRequest))
                .doOnSuccess(response -> log.info(response))
                .doOnError(ex -> log.error(ex.getMessage()));
    }

    @PostMapping("/create-user")
    public Mono<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
        return loginService.createUser(userDto)
                .doOnNext(response -> log.info(userDto))
                .doOnSuccess(response -> log.info(response))
                .doOnError(ex -> log.error(ex.getMessage()));
    }
}
