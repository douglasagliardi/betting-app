package com.sportygroup.betting.usecase;

public class InsufficientFundsException extends RuntimeException {

  public InsufficientFundsException(long walletId, long amount) {
    super(String.format("unable to place bet with amount of %d using wallet %d", amount, walletId));
  }
}
