package com.sportygroup.betting.infrastructure.externalapi.client;

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

  Collection<OpenF1Session> getSessions(@RequestParam("session_type") String eventType, @RequestParam("year") Integer year);
  Collection<OpenF1Driver> getDrivers(@RequestParam("session_key") String eventId);
}
