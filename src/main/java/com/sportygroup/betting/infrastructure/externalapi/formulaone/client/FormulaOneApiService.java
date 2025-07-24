package com.sportygroup.betting.infrastructure.externalapi.formulaone.client;

import com.sportygroup.betting.domain.FormulaOneEvents;
import com.sportygroup.betting.domain.FormulaOneRaceQuery;

public interface FormulaOneApiService {

  String getProvider();

  FormulaOneEvents getRaces(FormulaOneRaceQuery raceQuery);

  default boolean isFallback() {
    return false;
  }
}
