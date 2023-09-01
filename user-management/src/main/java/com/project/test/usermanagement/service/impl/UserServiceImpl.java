package com.project.test.usermanagement.service.impl;


import com.project.test.usermanagement.dto.UserDto;
import com.project.test.usermanagement.entity.UserEntity;
import com.project.test.usermanagement.exception.NotFoundException;
import com.project.test.usermanagement.exception.ProjectTestException;
import com.project.test.usermanagement.repository.UserRepository;
import com.project.test.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public Mono<UserDto> saveUser(UserDto userDto) {
        return userRepository.findByEmail(userDto.getEmail())
                .flatMap(userEntity -> Mono.error(new ProjectTestException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Ya existe un usuario con este mismo email: " + userEntity.getEmail())))
                .switchIfEmpty(Mono.just(userDto))
                .flatMap(user -> userRepository.save(modelMapper.map(user, UserEntity.class)))
                .map(userEntitySaved -> modelMapper.map(userEntitySaved, UserDto.class));
    }

    @Override
    public Flux<UserDto> findAllUser() {
        return userRepository.findAll()
                .flatMap(userEntity -> Mono.just(modelMapper.map(userEntity, UserDto.class)))
                .switchIfEmpty(response -> Flux.error(new NotFoundException(HttpStatus.NOT_FOUND,
                        "No existen usuarios")));
    }

    @Override
    public Mono<UserDto> findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .flatMap(userEntity -> Mono.just(modelMapper.map(userEntity, UserDto.class)))
                .switchIfEmpty(Mono.error(new NotFoundException(HttpStatus.NOT_FOUND,
                        "No existe usuario")));
    }


}
