package com.sportygroup.betting.infrastructure.externalapi.formulaone.client;

import java.util.Collection;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    value = "openf1-api",
    url = "${spring.cloud.openfeign.client.config.openf1-api.url}"
)
@Component
public interface OpenF1ApiClient {

  Collection<OpenF1Session> getSessions(
      @RequestParam(value = "session_type", required = false) String byEventType,
      @RequestParam(value = "country_code", required = false) String byCountry,
      @RequestParam(value = "year", required = false) Integer withYear);

  Collection<OpenF1Driver> getDrivers(@RequestParam("session_key") Integer bySessionKey);
}
