package com.project.test.usermanagement.service;

import com.project.test.usermanagement.dto.UserDto;
import com.project.test.usermanagement.entity.UserEntity;
import com.project.test.usermanagement.exception.ProjectTestException;
import com.project.test.usermanagement.repository.UserRepository;
import com.project.test.usermanagement.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    private UserDto userDto;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {

        userDto = UserDto.builder().name("Santiago Lozano")
                .email("santtiagolozano@gmail.com")
                .password("123").build();

        userEntity = UserEntity.builder()
                .id("1").email("santtiagolozano@gmail.com")
                .name("Santiago Lozano").build();
    }

    @Test
    public void create_user_return_ok() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Mono.empty());
        when(modelMapper.map(userDto, UserEntity.class)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(Mono.just(userEntity));
        when(modelMapper.map(userEntity, UserDto.class)).thenReturn(userDto);
        StepVerifier.create(userServiceImpl.saveUser(userDto))
                .assertNext(response -> { //then
                    assertEquals(response.getEmail(), userEntity.getEmail());
                    assertEquals(response.getName(), userEntity.getName());
                }).verifyComplete();
    }

    @Test
    public void create_user_return_error() {
        String messageError = "Ya existe un usuario con este mismo email: " + userEntity.getEmail();
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Mono.error(new ProjectTestException(HttpStatus.INTERNAL_SERVER_ERROR,
                messageError)));
        StepVerifier.create(userServiceImpl.saveUser(userDto))
                .expectErrorMatches(response -> response instanceof ProjectTestException
                        && response.getMessage().equals(messageError)).verify();
    }

    @Test
    public void get_allUser_return_ok() {
        when(userRepository.findAll()).thenReturn(Flux.just(userEntity));
        when(modelMapper.map(userEntity, UserDto.class)).thenReturn(userDto);
        StepVerifier.create(userServiceImpl.findAllUser())
                .assertNext(response -> { //then
                    assertEquals(response.getEmail(), userDto.getEmail());
                    assertEquals(response.getName(), userEntity.getName());
                }).verifyComplete();
    }

    @Test
    public void get_allUser_return_error_notFound() {
        String messageError = "No existen usuarios.";
        when(userRepository.findAll()).thenReturn(Flux.error(new ProjectTestException(HttpStatus.NOT_FOUND,
                messageError)));
        StepVerifier.create(userServiceImpl.findAllUser())
                .expectErrorMatches(response -> response instanceof ProjectTestException
                        && response.getMessage().equals(messageError)).verify();
    }
}
