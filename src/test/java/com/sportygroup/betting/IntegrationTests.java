package com.sportygroup.betting;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.testcontainers.junit.jupiter.Testcontainers;

@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
//@Import(TestcontainersConfiguration.class)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(
    classes = BettingApplication.class,
    webEnvironment = WebEnvironment.RANDOM_PORT
)
@Target(ElementType.TYPE)
@Testcontainers
public @interface IntegrationTests { }
