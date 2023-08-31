package com.project.test.securitygateway.controller;

import com.project.test.securitygateway.dto.UserDto;
import com.project.test.securitygateway.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public Flux<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .doOnNext(response -> log.info(response))
                .doOnError(ex -> log.error(ex.getMessage()));
    }

    @GetMapping("/{email}")
    public Mono<UserDto> getUserByEmail(@PathVariable("email") String email) {
        return userService.findUserByEmail(email)
                .doOnNext(response -> log.info(email))
                .doOnSuccess(response -> log.info(response))
                .doOnError(ex -> log.error(ex.getMessage()));
    }
}
