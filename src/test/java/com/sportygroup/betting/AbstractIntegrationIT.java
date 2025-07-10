package com.sportygroup.betting;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@IntegrationTests
public abstract class AbstractIntegrationIT {

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> DATABASE_CONTAINER = new PostgreSQLContainer<>("postgres:17-alpine");

  @Container
  @ServiceConnection
  static RabbitMQContainer AMQP_CONTAINER = new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.7-management-alpine"));

  //Workaround due to flyway trying to run migrations before the container starts
  @BeforeAll
  static void beforeAll() {
    DATABASE_CONTAINER.start();
    AMQP_CONTAINER.start();
  }

  @AfterAll
  static void tearDown() {
    DATABASE_CONTAINER.stop();
    AMQP_CONTAINER.stop();
  }

  @Autowired
  protected MockMvc mockMvc;

  @AfterEach
  void reset() {
    WireMock.reset();
  }
}
