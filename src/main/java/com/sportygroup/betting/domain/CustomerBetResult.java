package com.sportygroup.betting.domain;

import org.springframework.util.Assert;

public record CustomerBetResult(long betId, long walletId, long amount, boolean succeeded) {

  public static CustomerBetResult winner(final long betId, final long walletId, final long amount) {
    Assert.isTrue(amount > 0, "Amount must be greater than zero");
    return new CustomerBetResult(betId, walletId, amount, true);
  }

  public static CustomerBetResult loser(final long betId, final long walletId, final long amount) {
    Assert.isTrue(amount < 0, "Amount must be greater than zero");
    return new CustomerBetResult(betId, walletId, amount, false);
  }
}
