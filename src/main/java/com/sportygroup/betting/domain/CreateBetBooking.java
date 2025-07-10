package com.sportygroup.betting.domain;

public record CreateBetBooking(long userId, long eventId, long amount, long odd) { }
