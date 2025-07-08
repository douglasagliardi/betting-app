package com.sportygroup.betting.infrastructure.externalapi.formulaone;

import com.sportygroup.betting.infrastructure.externalapi.formulaone.client.OpenF1ApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DefaultFormulaOneService implements FormulaOneService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultFormulaOneService.class);

  private final OpenF1ApiClient httpClient;
  private final FormulaOneMapper mapper;

  public DefaultFormulaOneService(final OpenF1ApiClient httpClient, final FormulaOneMapper mapper) {
    this.httpClient = httpClient;
    this.mapper = mapper;
  }

  @Override
  public FormulaOneEvents getEvents(final FormulaOneParams params) {
    LOGGER.atDebug().setMessage("retrieving event(s) using {} as parameters from partner API").addArgument(params).log();
    final var sessions = httpClient.getSessions(params.eventType(), params.countryCode(), params.year());
    final var result = sessions.stream()
        .map(event -> {
          final var drivers = httpClient.getDrivers(event.sessionKey()).stream().map(mapper::toDomain).toList();
          return new FormulaOneEvent(event.sessionKey(), drivers);
        })
        .toList();
    return new FormulaOneEvents(result);
  }
}
