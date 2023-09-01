package com.project.test.securitygateway.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;

class AbstractRepositoryTest {
    protected static MockWebServer mockWebServer;
    protected static String mockWsUri;
    protected static ObjectMapper objectMapper;

    @BeforeAll
    static void setUp() throws IOException {
        objectMapper = new ObjectMapper();
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        mockWsUri = String.format("http://localhost:%s",
                mockWebServer.getPort());
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

}