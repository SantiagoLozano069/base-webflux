package com.project.test.securitygateway.service.impl;

import com.project.test.securitygateway.dto.UserDto;
import com.project.test.securitygateway.exception.UnauthorizedException;
import com.project.test.securitygateway.securityJwt.component.Encoder;
import com.project.test.securitygateway.securityJwt.dto.AuthRequest;
import com.project.test.securitygateway.securityJwt.dto.AuthResponse;
import com.project.test.securitygateway.securityJwt.dto.Role;
import com.project.test.securitygateway.securityJwt.dto.User;
import com.project.test.securitygateway.securityJwt.util.JWTUtil;
import com.project.test.securitygateway.service.LoginService;
import com.project.test.securitygateway.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    private final Encoder encoder;
    private final JWTUtil jwtUtil;
    private final UserService userService;

    @Value("application.error.message.invalid-credentials")
    private String errorCredentials;

    @Override
    public Mono<AuthResponse> login(AuthRequest loginRequest) {
        return userService.findUserByEmail(loginRequest.getUsername())
                .filter(userDto -> encoder.encode(loginRequest.getPassword()).equals(userDto.getPassword()))
                .map(userDto -> new AuthResponse(jwtUtil.generateToken(this.userDtoToUser(userDto))))
                .switchIfEmpty(Mono.error(new UnauthorizedException(HttpStatus.UNAUTHORIZED, errorCredentials)));
    }

    private User userDtoToUser(UserDto userDto){
        return User.builder()
                .username(userDto.getEmail())
                .password(userDto.getPassword())
                .enabled(true)
                .roles(Arrays.asList(Role.ROLE_USER))
                .build();
    }


    @Override
    public Mono<UserDto> createUser(UserDto userDto) {
        return userService.createUser(userDto);
    }
}
