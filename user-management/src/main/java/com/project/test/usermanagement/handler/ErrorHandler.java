package com.project.test.usermanagement.handler;

import com.project.test.usermanagement.exception.NotFoundException;
import com.project.test.usermanagement.exception.ProjectTestException;
import com.project.test.usermanagement.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(ProjectTestException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(ProjectTestException ex)
    {
        return new ErrorResponse(ex.getStatusCode().toString(), ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse NotFoundExceptionHandler(NotFoundException ex)
    {
        return new ErrorResponse(ex.getHttpStatus().toString(), ex.getMessage());
    }


}
