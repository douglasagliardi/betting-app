package com.sportygroup.betting.usecase;

import com.sportygroup.betting.domain.FormulaOneEvents;
import com.sportygroup.betting.domain.FormulaOneRaceQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DefaultFormulaOneService implements FormulaOneService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultFormulaOneService.class);

  private final ProviderServiceSelectable providerService;

  public DefaultFormulaOneService(final ProviderServiceSelectable providerService) {
    this.providerService = providerService;
  }

  @Override
  public FormulaOneEvents getDriverOddsForEvents(final FormulaOneRaceQuery params) {
    final var apiSvc = providerService.getApiService();
    LOGGER.atInfo().setMessage("provider {} has been selected for querying the odds for events using the following parameters {}")
        .addArgument(apiSvc.getProvider())
        .addArgument(params)
        .log();
    return apiSvc.getRaces(params);
  }
}
