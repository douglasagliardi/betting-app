package com.sportygroup.betting.infrastructure.externalapi.formulaone.client.livepulse;

import com.sportygroup.betting.domain.FormulaOneDriver;
import org.springframework.stereotype.Component;

@Component
public class DefaultF1LivePulseMapper implements F1LivePulseMapper {

  @Override
  public FormulaOneDriver toDomain(final F1LivePulseDriver driver) {
    return new FormulaOneDriver(driver.id(), driver.firstName() + driver.firstName(), odd());
  }
}
