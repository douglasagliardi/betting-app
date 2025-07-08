package com.sportygroup.betting.infrastructure.externalapi.formulaone;

import com.sportygroup.betting.infrastructure.externalapi.formulaone.client.OpenF1Driver;

public interface FormulaOneMapper {

  FormulaOneDriver toDomain(OpenF1Driver driver);
}
