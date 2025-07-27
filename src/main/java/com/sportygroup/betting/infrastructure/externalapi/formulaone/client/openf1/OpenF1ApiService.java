package com.sportygroup.betting.infrastructure.externalapi.formulaone.client.openf1;

import com.sportygroup.betting.domain.FormulaOneDriver;
import com.sportygroup.betting.domain.FormulaOneEvent;
import com.sportygroup.betting.domain.FormulaOneEventLocation;
import com.sportygroup.betting.domain.FormulaOneEvents;
import com.sportygroup.betting.domain.FormulaOneRaceQuery;
import com.sportygroup.betting.infrastructure.externalapi.formulaone.client.FormulaOneApiService;
import java.util.Collection;
import org.springframework.stereotype.Service;

@Service
public class OpenF1ApiService implements FormulaOneApiService {

  private final OpenF1ApiClient httpClient;
  private final OpenF1Mapper responseMapper;

  public OpenF1ApiService(final OpenF1ApiClient httpClient, final OpenF1Mapper responseMapper) {
    this.httpClient = httpClient;
    this.responseMapper = responseMapper;
  }

  @Override
  public String getProvider() {
    return "open-f1";
  }

  @Override
  public boolean isFallback() {
    return true;
  }

  @Override
  public FormulaOneEvents getRaces(final FormulaOneRaceQuery raceQuery) {
    final var sessions = httpClient.getSessions(raceQuery.eventType(), raceQuery.countryCode(), raceQuery.year());
    final var result = sessions.stream()
        .map(event -> {
          final var drivers = getDrivers(event.sessionKey());
          return new FormulaOneEvent(
              event.sessionKey(),
              event.circuitShortName(),
              new FormulaOneEventLocation(event.countryName(), event.countryCode()),
              drivers
          );
        })
        .toList();
    return new FormulaOneEvents(result);
  }

  private Collection<FormulaOneDriver> getDrivers(final int eventId) {
    return httpClient.getDrivers(eventId).stream().map(responseMapper::toDomain).toList();
  }
}
