package com.project.test.securitygateway.service;

import com.project.test.securitygateway.dto.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserDto> createUser(UserDto userDto);

    Mono<UserDto> findUserByEmail(String email);

    Flux<UserDto> findAllUsers();
}
