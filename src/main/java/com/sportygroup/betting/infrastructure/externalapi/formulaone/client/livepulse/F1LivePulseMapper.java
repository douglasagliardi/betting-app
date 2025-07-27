package com.sportygroup.betting.infrastructure.externalapi.formulaone.client.livepulse;

import com.sportygroup.betting.domain.FormulaOneDriver;
import com.sportygroup.betting.infrastructure.externalapi.formulaone.client.OddProduceable;

public interface F1LivePulseMapper extends OddProduceable {

  FormulaOneDriver toDomain(F1LivePulseDriver driver);
}
