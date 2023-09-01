package com.project.test.securitygateway.service.impl;

import com.project.test.securitygateway.dto.UserDto;
import com.project.test.securitygateway.repository.UserRepository;
import com.project.test.securitygateway.securityJwt.component.Encoder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private Encoder encoder;
    @Mock
    private UserRepository userRepository;

    @Test
    void createUser_when_return_success() {
        //given
        UserDto request = new UserDto(null, "santiago",
                "santtiagolozano@gmail.com", "12345");
        UserDto response = new UserDto("1", "santiago",
                "santtiagolozano@gmail.com", "asdadds");
        //when
        when(encoder.encode(request.getPassword())).thenReturn("asdadds");
        when(userRepository.createUser(request)).thenReturn(Mono.just(response));

        //then
        StepVerifier.create(userService.createUser(request))
                .assertNext(res -> {
                    assertNotNull(res);
                    assertEquals(res.getEmail(), request.getEmail());
                    assertEquals(res.getName(), request.getName());
                })
                .verifyComplete();
    }

    @Test
    void findUserByEmail_when_return_success() {
        //given
        String request = "santtiagolozano@gmail.com";
        UserDto response = new UserDto("1", "santiago",
                "santtiagolozano@gmail.com", "asdadds");
        //when
        when(userRepository.getUserByEmail(request)).thenReturn(Mono.just(response));

        //then
        StepVerifier.create(userService.findUserByEmail(request))
                .assertNext(res -> {
                    assertNotNull(res);
                    assertEquals(res.getEmail(), request);
                })
                .verifyComplete();
    }

    @Test
    void findAllUsers_when_return_success() {
        //given
        List<UserDto> response = Arrays.asList(new UserDto("1", "santiago",
                "santtiagolozano@gmail.com", "asdadds"),
                new UserDto("2", "santiago2",
                        "santtiagolozano2@gmail.com", "asdadds2"));
        //when
        when(userRepository.getAllUsers()).thenReturn(Flux.fromIterable(response));

        //then
        StepVerifier.create(userService.findAllUsers())
                .expectNextMatches(userDto -> userDto.getId() != null)
                .assertNext(userDto -> assertNotNull(userDto.getEmail()))
                .verifyComplete();
    }
}