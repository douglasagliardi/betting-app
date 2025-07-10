package com.sportygroup.betting;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

@IntegrationTests
public abstract class AbstractIntegrationIT {

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> DATABASE_CONTAINER = new PostgreSQLContainer<>("postgres:17-alpine");

  //Workaround due to flyway trying to run migrations before the container starts
  static {
    DATABASE_CONTAINER.start();
  }

  @Autowired
  protected MockMvc mockMvc;

  @AfterEach
  void reset() {
    WireMock.reset();
  }
}
