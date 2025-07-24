package com.sportygroup.betting.infrastructure.externalapi.formulaone.client.livepulse;

import java.util.Collection;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    value = "f1-livepulse-api",
    url = "${spring.cloud.openfeign.client.config.f1-livepulse-api.url}"
)
@Component
public interface F1LivePulseApiClient {

  @GetMapping(
      value = "/api/v1/races",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  Collection<F1LivePulseSession> getSessions(
      @RequestParam(value = "session_type") String byEventType,
      @RequestParam(value = "country_code", required = false) String byCountry,
      @RequestParam(value = "year", required = false) Integer withYear);

  @GetMapping(
      value = "/api/v1/pilots",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  Collection<F1LivePulseDriver> getDrivers(@RequestParam("race_id") Integer byRaceId);
}
