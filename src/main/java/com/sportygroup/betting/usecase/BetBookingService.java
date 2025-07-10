package com.sportygroup.betting.usecase;

import com.sportygroup.betting.api.FormulaOneEventResultRequest;
import com.sportygroup.betting.api.PlaceBetRequest;

public interface BetBookingService {

  BookedBet create(PlaceBetRequest booking);
  void completeRace(final FormulaOneEventResultRequest eventResult);
}
