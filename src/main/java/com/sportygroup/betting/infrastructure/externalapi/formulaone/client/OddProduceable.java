package com.sportygroup.betting.infrastructure.externalapi.formulaone.client;

import java.security.SecureRandom;

public interface OddProduceable {

  SecureRandom SECURE_RANDOM = new SecureRandom();

  default int odd() {
    return SECURE_RANDOM.nextInt(2, 5);
  }
}
