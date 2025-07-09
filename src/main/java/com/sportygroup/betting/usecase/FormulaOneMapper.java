package com.sportygroup.betting.usecase;

import com.sportygroup.betting.domain.FormulaOneDriver;
import com.sportygroup.betting.infrastructure.externalapi.formulaone.client.OpenF1Driver;

public interface FormulaOneMapper {

  FormulaOneDriver toDomain(OpenF1Driver driver);
}
