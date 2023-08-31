package com.project.test.securitygateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String password;
}
