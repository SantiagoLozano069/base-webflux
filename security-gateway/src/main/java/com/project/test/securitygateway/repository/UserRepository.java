package com.project.test.securitygateway.repository;

import com.project.test.securitygateway.dto.UserDto;
import com.project.test.securitygateway.exception.NotFoundException;
import com.project.test.securitygateway.exception.SecurityGatewayException;
import com.project.test.securitygateway.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UserRepository extends AbstractRepository {

    private final String uri;

    @Value("${application.error.message.not-found-user}")
    private String notFoundUserError;

    protected UserRepository(@Value("${application.resources.user-management.services.uri}") String uri,
                             ReactorClientHttpConnector connector) {
        super(uri, connector);
        this.uri = uri;
    }

    public Flux<UserDto> getAllUsers() {
        return super.getClient().get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatus.UNAUTHORIZED::equals, clientResponse -> Mono.error(new UnauthorizedException(clientResponse.statusCode(),
                        clientResponse.statusCode().getReasonPhrase())))
                .onStatus(HttpStatus.FORBIDDEN::equals, clientResponse -> Mono.error(new UnauthorizedException(clientResponse.statusCode(),
                        clientResponse.statusCode().getReasonPhrase())))
                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse ->
                        Mono.error(new NotFoundException(clientResponse.statusCode(),
                                notFoundUserError)))
                .onStatus(HttpStatus.INTERNAL_SERVER_ERROR::equals, clientResponse ->
                        Mono.error(new SecurityGatewayException(clientResponse.statusCode(),
                                clientResponse.statusCode().getReasonPhrase())))
                .bodyToFlux(UserDto.class);
    }

    public Mono<UserDto> getUserByEmail(String email) {
        return super.getClient().get()
                .uri(new StringBuilder(uri).append("/").append(email).toString())
                .retrieve()
                .onStatus(HttpStatus.UNAUTHORIZED::equals, clientResponse -> Mono.error(new UnauthorizedException(clientResponse.statusCode(),
                        clientResponse.statusCode().getReasonPhrase())))
                .onStatus(HttpStatus.FORBIDDEN::equals, clientResponse -> Mono.error(new UnauthorizedException(clientResponse.statusCode(),
                        clientResponse.statusCode().getReasonPhrase())))
                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse ->
                        Mono.error(new NotFoundException(clientResponse.statusCode(),
                                notFoundUserError)))
                .onStatus(HttpStatus.INTERNAL_SERVER_ERROR::equals, clientResponse ->
                        Mono.error(new SecurityGatewayException(clientResponse.statusCode(),
                                clientResponse.statusCode().getReasonPhrase())))
                .bodyToMono(UserDto.class);
    }

    public Mono<UserDto> createUser(UserDto userDto) {
        return super.getClient().post()
                .uri(uri)
                .bodyValue(userDto)
                .retrieve()
                .onStatus(HttpStatus.UNAUTHORIZED::equals, clientResponse -> Mono.error(new UnauthorizedException(clientResponse.statusCode(),
                        clientResponse.statusCode().getReasonPhrase())))
                .onStatus(HttpStatus.FORBIDDEN::equals, clientResponse -> Mono.error(new UnauthorizedException(clientResponse.statusCode(),
                        clientResponse.statusCode().getReasonPhrase())))
                .onStatus(HttpStatus.INTERNAL_SERVER_ERROR::equals, clientResponse ->
                        Mono.error(new SecurityGatewayException(clientResponse.statusCode(),
                                clientResponse.statusCode().getReasonPhrase())))
                .bodyToMono(UserDto.class);
    }
}
