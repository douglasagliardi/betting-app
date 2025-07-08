package com.sportygroup.betting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BettingApplication {

  public static void main(final String... args) {
    SpringApplication.run(BettingApplication.class, args);
  }
}
