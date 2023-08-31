package com.project.test.securitygateway.repository;

import lombok.Getter;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

@Getter
public abstract class AbstractRepository {
    private final WebClient client;

    protected AbstractRepository(String uri, ReactorClientHttpConnector connector) {
        this.client = WebClient.builder().baseUrl(uri).clientConnector(connector).build();
    }
}
