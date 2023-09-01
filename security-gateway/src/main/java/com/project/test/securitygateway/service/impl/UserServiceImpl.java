package com.project.test.securitygateway.service.impl;

import com.project.test.securitygateway.dto.UserDto;
import com.project.test.securitygateway.repository.UserRepository;
import com.project.test.securitygateway.securityJwt.component.Encoder;
import com.project.test.securitygateway.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final Encoder encoder;
    private final UserRepository userRepository;

    @Override
    public Mono<UserDto> createUser(UserDto userDto) {
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        return userRepository.createUser(userDto);
    }

    @Override
    public Mono<UserDto> findUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public Flux<UserDto> findAllUsers() {
        return userRepository.getAllUsers();
    }
}
