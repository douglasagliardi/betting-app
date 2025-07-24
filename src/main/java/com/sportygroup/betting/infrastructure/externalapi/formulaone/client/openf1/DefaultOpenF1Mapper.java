package com.sportygroup.betting.infrastructure.externalapi.formulaone.client.openf1;

import com.sportygroup.betting.domain.FormulaOneDriver;
import org.springframework.stereotype.Component;

@Component
public class DefaultOpenF1Mapper implements OpenF1Mapper {

  @Override
  public FormulaOneDriver toDomain(final OpenF1Driver dto) {
    return new FormulaOneDriver(dto.driverNumber(), getFullName(dto), odd());
  }

  private String getFullName(final OpenF1Driver dto) {
    return dto.firstName() + " " + dto.lastName();
  }

}
