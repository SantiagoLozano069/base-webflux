package com.project.test.usermanagement.controller;

import com.project.test.usermanagement.dto.UserDto;
import com.project.test.usermanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserDto> saveUser(@RequestBody @Valid UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<UserDto> findAllUser() {
        return userService.findAllUser();
    }


    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserDto> findAllUser(@PathVariable(value = "email", required = true) String email) {
        return userService.findUserByEmail(email);
    }
}
