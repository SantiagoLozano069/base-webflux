package com.project.test.securitygateway.securityJwt.service;

import com.project.test.securitygateway.repository.UserRepository;
import com.project.test.securitygateway.securityJwt.dto.Role;
import com.project.test.securitygateway.securityJwt.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Mono<User> findByUsername(String username) {
        return userRepository.getUserByEmail(username)
                .flatMap(userEntity -> Mono.just(new User(userEntity.getEmail(),
                        userEntity.getPassword(), true,
                        Arrays.asList(Role.ROLE_USER))))
                .switchIfEmpty(Mono.empty());
    }
}
