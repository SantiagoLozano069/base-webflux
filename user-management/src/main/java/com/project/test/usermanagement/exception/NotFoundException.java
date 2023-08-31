package com.project.test.usermanagement.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends Exception {
    private final HttpStatus httpStatus;
    private final String description;

    public NotFoundException(HttpStatus httpStatus, String description) {
        super(description);
        this.httpStatus = httpStatus;
        this.description = description;
    }
}
