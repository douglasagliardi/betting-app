package com.sportygroup.betting.usecase;

import com.sportygroup.betting.domain.FormulaOneEvents;
import com.sportygroup.betting.domain.FormulaOneRaceQuery;

public interface FormulaOneService {

  FormulaOneEvents getDriverOddsForEvents(FormulaOneRaceQuery params);
}
