package com.sportygroup.betting.infrastructure.externalapi.formulaone.client.livepulse;

import com.sportygroup.betting.domain.FormulaOneDriver;
import com.sportygroup.betting.domain.FormulaOneEvent;
import com.sportygroup.betting.domain.FormulaOneEventLocation;
import com.sportygroup.betting.domain.FormulaOneEvents;
import com.sportygroup.betting.domain.FormulaOneRaceQuery;
import com.sportygroup.betting.infrastructure.externalapi.formulaone.client.FormulaOneApiService;
import java.util.Collection;
import org.springframework.stereotype.Component;

@Component
public class LivePulseF1ApiService implements FormulaOneApiService {

  private final F1LivePulseApiClient httpClient;
  private final F1LivePulseMapper mapper;

  public LivePulseF1ApiService(final F1LivePulseApiClient httpClient, final F1LivePulseMapper mapper) {
    this.httpClient = httpClient;
    this.mapper = mapper;
  }

  @Override
  public String getProvider() {
    return "live-pulse";
  }

  @Override
  public FormulaOneEvents getRaces(final FormulaOneRaceQuery raceQuery) {
    final var sessions = httpClient.getSessions(raceQuery.eventType(), raceQuery.countryCode(), raceQuery.year());
    final var result = sessions.stream()
        .map(event -> {
          final var drivers = getDrivers(event.id());
          return new FormulaOneEvent(
              event.id(),
              event.name(),
              new FormulaOneEventLocation(event.countryCode(), event.countryCode()),
              drivers
          );
        })
        .toList();
    return new FormulaOneEvents(result);
  }

  private Collection<FormulaOneDriver> getDrivers(final int eventId) {
    return httpClient.getDrivers(eventId).stream().map(mapper::toDomain).toList();
  }
}
