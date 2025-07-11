package com.sportygroup.betting.api;

public record PlaceBetRequest(long walletId, long eventId, int driverId, long amount, int odd) {}
