package com.sportygroup.betting.api;

//TODO: add validation on all fields!
public record PlaceBetRequest(long walletId, long eventId, long amount) {}
