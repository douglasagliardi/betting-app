package com.sportygroup.betting.usecase;

public class NotEventWinnerFoundException extends RuntimeException {

  public NotEventWinnerFoundException(final long eventId) {
    super("winner for event with id " + eventId + " was not found");
  }
}
