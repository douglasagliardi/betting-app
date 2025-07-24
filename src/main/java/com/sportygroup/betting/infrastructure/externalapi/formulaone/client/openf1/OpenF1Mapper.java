package com.sportygroup.betting.infrastructure.externalapi.formulaone.client.openf1;

import com.sportygroup.betting.domain.FormulaOneDriver;
import com.sportygroup.betting.infrastructure.externalapi.formulaone.client.OddProduceable;

public interface OpenF1Mapper extends OddProduceable {

  FormulaOneDriver toDomain(OpenF1Driver driver);
}
