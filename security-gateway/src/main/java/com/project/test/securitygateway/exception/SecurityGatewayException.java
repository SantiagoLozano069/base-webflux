package com.project.test.securitygateway.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SecurityGatewayException extends Exception {
    private final HttpStatus httpStatus;

    private final String description;

    public SecurityGatewayException(HttpStatus httpStatus, String description) {
        super(description);
        this.httpStatus = httpStatus;
        this.description = description;
    }
}
