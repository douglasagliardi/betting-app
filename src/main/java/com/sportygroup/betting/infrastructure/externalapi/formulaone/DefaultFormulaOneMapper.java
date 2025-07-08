package com.sportygroup.betting.infrastructure.externalapi.formulaone;

import com.sportygroup.betting.infrastructure.externalapi.formulaone.client.OpenF1Driver;
import java.security.SecureRandom;
import org.springframework.stereotype.Service;

@Service
public class DefaultFormulaOneMapper implements FormulaOneMapper {

  private static final SecureRandom SECURE_RANDOM = new SecureRandom();

  @Override
  public FormulaOneDriver toDomain(final OpenF1Driver dto) {
    return new FormulaOneDriver(dto.driverNumber(), getFullName(dto), getOdd());
  }

  private String getFullName(final OpenF1Driver dto) {
    return dto.firstName() + " " + dto.lastName();
  }

  private int getOdd() {
    return SECURE_RANDOM.nextInt(2, 5);
  }
}
