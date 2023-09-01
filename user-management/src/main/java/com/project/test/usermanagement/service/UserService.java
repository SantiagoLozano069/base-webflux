package com.project.test.usermanagement.service;

import com.project.test.usermanagement.dto.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserDto> saveUser(UserDto userDto);

    Flux<UserDto> findAllUser();

    Mono<UserDto> findUserByEmail(String email);
}
