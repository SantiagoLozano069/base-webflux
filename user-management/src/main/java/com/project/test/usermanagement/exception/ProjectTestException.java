package com.project.test.usermanagement.exception;

import org.springframework.http.HttpStatus;


public class ProjectTestException extends Exception{

    private final HttpStatus statusCode;
    public ProjectTestException (HttpStatus statusCode, String message){
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
