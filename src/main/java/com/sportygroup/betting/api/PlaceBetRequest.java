package com.sportygroup.betting.api;

public record PlaceBetRequest(long walletId, long eventId, int playerId, long amount, int odd) {}
