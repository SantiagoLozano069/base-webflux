package com.project.test.securitygateway.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnauthorizedException extends RuntimeException{

    private final HttpStatus httpStatus;

    private final String description;

    public UnauthorizedException(HttpStatus httpStatus, String description) {
        super(description);
        this.httpStatus = httpStatus;
        this.description = description;
    }
}
