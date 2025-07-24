package com.sportygroup.betting.infrastructure.externalapi.formulaone.client;

import com.sportygroup.betting.domain.FormulaOneDriver;
import com.sportygroup.betting.infrastructure.externalapi.formulaone.client.livepulse.F1LivePulseDriver;

public interface F1LivePulseMapper extends OddProduceable {

  FormulaOneDriver toDomain(F1LivePulseDriver driver);
}
