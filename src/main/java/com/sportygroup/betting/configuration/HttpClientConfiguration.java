package com.sportygroup.betting.configuration;

import feign.okhttp.OkHttpClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {"com.sportygroup.betting.infrastructure.externalapi.formulaone.client"})
public class HttpClientConfiguration {

  @Bean
  public OkHttpClient client() {
    return new OkHttpClient();
  }
}
