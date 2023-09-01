package com.project.test.usermanagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
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
