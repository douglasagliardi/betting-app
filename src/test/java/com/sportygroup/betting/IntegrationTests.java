package com.sportygroup.betting;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.testcontainers.junit.jupiter.Testcontainers;

@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
@SpringBootTest(
    classes = BettingApplication.class,
    webEnvironment = WebEnvironment.RANDOM_PORT
)
@Testcontainers
public @interface IntegrationTests { }
