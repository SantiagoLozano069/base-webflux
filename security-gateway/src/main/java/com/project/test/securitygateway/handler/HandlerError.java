
package com.project.test.securitygateway.handler;

import com.project.test.securitygateway.dto.ErrorResponse;
import com.project.test.securitygateway.exception.NotFoundException;
import com.project.test.securitygateway.exception.SecurityGatewayException;
import com.project.test.securitygateway.exception.UnauthorizedException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebInputException;

import java.net.ConnectException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class HandlerError {

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse loginUnauthorizedExceptionHandler(UnauthorizedException e) {
        return ErrorResponse.builder().status(e.getHttpStatus().toString()).description(e.getDescription()).build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse NotFoundExceptionHandler(NotFoundException e) {
        return ErrorResponse.builder().status(e.getHttpStatus().toString()).description(e.getDescription()).build();
    }

    @ExceptionHandler(SecurityGatewayException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse securityGatewayExceptionExceptionHandler(SecurityGatewayException e) {
        return ErrorResponse.builder().status(e.getHttpStatus().toString()).description(e.getDescription()).build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse loginExceptionHandler(Exception e) {
        return ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.toString()).description(e.getMessage()).build();
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        String errorFields = exception.getBindingResult().getFieldErrors().stream().map(FieldError::getField)
                .collect(Collectors.joining(", "));
        return ErrorResponse.builder().status(HttpStatus.BAD_REQUEST.toString())
                .description("Invalid Fields: " + errorFields).build();
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse httpRequestMethodNotSupportedExceptionHandler(HttpMessageNotReadableException exception) {
        return ErrorResponse.builder().status(HttpStatus.BAD_REQUEST.toString())
                .description("Request could not be deserialized.").build();
    }

    @ExceptionHandler({ServerWebInputException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse serverWebInputException(ServerWebInputException exception) {
        return ErrorResponse.builder().status(HttpStatus.BAD_REQUEST.toString())
                .description("Request could not be deserialized.").build();
    }

    @ExceptionHandler({WebExchangeBindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse webExchangeBindExceptionHandler(WebExchangeBindException exception) {
        String errorFields = exception.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getField).collect(Collectors.joining(", "));
        return ErrorResponse.builder().status(HttpStatus.BAD_REQUEST.toString())
                .description("Invalid Fields: " + errorFields).build();
    }

    @ExceptionHandler({ConnectException.class})
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public ErrorResponse connectTimeoutExceptionHandler(ConnectException exception) {
        return ErrorResponse.builder().status(HttpStatus.REQUEST_TIMEOUT.toString()).description(exception.getMessage()).build();
    }

    @ExceptionHandler({WebClientResponseException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResponse webClientResponseExceptionHandler(WebClientResponseException exception) {
        return ErrorResponse.builder().status(HttpStatus.SERVICE_UNAVAILABLE.toString()).description(exception.getMessage()).build();
    }
}
