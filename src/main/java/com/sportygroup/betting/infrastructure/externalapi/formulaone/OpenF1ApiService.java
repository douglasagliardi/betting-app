package com.sportygroup.betting.infrastructure.externalapi.formulaone;

import java.util.Collection;

public interface OpenF1ApiService {

  FormulaOneEvents findEvents(final FormulaOneParams params);
  Collection<FormulaOneDriver> findDrivers(final int eventId);
}
