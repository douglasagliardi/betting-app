package com.sportygroup.betting.infrastructure.externalapi.formulaone.client;

import com.sportygroup.betting.domain.FormulaOneDriver;
import com.sportygroup.betting.infrastructure.externalapi.formulaone.client.livepulse.F1LivePulseDriver;
import org.springframework.stereotype.Component;

@Component
public class DefaultF1LivePulseMapper implements F1LivePulseMapper {

  @Override
  public FormulaOneDriver toDomain(final F1LivePulseDriver driver) {
    return new FormulaOneDriver(driver.id(), driver.firstName() + driver.firstName(), odd());
  }
}
