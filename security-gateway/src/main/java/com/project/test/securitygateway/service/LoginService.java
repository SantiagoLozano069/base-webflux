package com.project.test.securitygateway.service;

import com.project.test.securitygateway.dto.UserDto;
import com.project.test.securitygateway.securityJwt.dto.AuthRequest;
import com.project.test.securitygateway.securityJwt.dto.AuthResponse;
import reactor.core.publisher.Mono;

public interface LoginService {

    Mono<AuthResponse> login (AuthRequest loginRequest);

    Mono<UserDto> createUser (UserDto userDto);
}
