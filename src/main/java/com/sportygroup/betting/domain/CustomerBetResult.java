package com.sportygroup.betting.domain;

public record CustomerBetResult(long betId, long walletId, long amount, boolean succeeded) { }
