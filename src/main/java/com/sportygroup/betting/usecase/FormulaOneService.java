package com.sportygroup.betting.usecase;

import com.sportygroup.betting.domain.FormulaOneEvents;
import com.sportygroup.betting.domain.FormulaOneParams;

public interface FormulaOneService {

  FormulaOneEvents getEvents(FormulaOneParams params);
}
