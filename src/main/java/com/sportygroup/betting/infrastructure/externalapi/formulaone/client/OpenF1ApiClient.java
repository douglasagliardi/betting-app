package com.sportygroup.betting.infrastructure.externalapi.formulaone.client;

import java.util.Collection;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    value = "openf1-api",
    url = "${spring.cloud.openfeign.client.config.openf1-api.url}"
)
@Component
public interface OpenF1ApiClient {

  @GetMapping(
      value = "/v1/sessions",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  Collection<OpenF1Session> getSessions(
      @RequestParam(value = "session_type") String byEventType,
      @RequestParam(value = "country_code", required = false) String byCountry,
      @RequestParam(value = "year", required = false) Integer withYear);

  @GetMapping(
      value = "/v1/drivers",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  Collection<OpenF1Driver> getDrivers(@RequestParam("session_key") Integer bySessionKey);
}
