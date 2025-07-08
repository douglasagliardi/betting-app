package com.sportygroup.betting.infrastructure.externalapi.formulaone;

import com.sportygroup.betting.infrastructure.externalapi.formulaone.client.OpenF1Driver;
import org.springframework.stereotype.Service;

@Service
public class DefaultFormulaOneMapper implements FormulaOneMapper {

  @Override
  public FormulaOneDriver toDomain(final OpenF1Driver driver) {
    return null;
  }
}
