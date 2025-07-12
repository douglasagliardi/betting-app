package com.sportygroup.betting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class BettingApplication {

  public static void main(final String... args) {
    SpringApplication.run(BettingApplication.class, args);
  }
}
