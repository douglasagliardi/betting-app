package com.sportygroup.betting.infrastructure.message;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sportygroup.betting.domain.CustomerBetResult;
import com.sportygroup.betting.infrastructure.database.BetBookingRepository;
import com.sportygroup.betting.infrastructure.database.WalletRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Bet Result Processing - adjust balance for winners and complete bets for bet")
@ExtendWith(MockitoExtension.class)
class BetBookingProcessableTest {

  @Mock
  private WalletRepository walletRepository;
  @Mock
  private BetBookingRepository betBookingRepository;
  @InjectMocks
  private BetBookingProcessable betProcessable;

  @DisplayName("bet winner should update their balance with expected amount and complete bet")
  @Test
  void betWinnerShouldUpdateWalletBalanceSuccess() {

    final var walletId = 1L;
    final var betAmount = 100L;
    final var betId = 1L;
    final var betResult = CustomerBetResult.winner(betId, walletId, betAmount);

    when(walletRepository.updateBalance(walletId, betAmount)).thenReturn(1);
    doNothing().when(betBookingRepository).completeBooking(betId, walletId);

    betProcessable.processCustomerBet(betResult);

    verify(walletRepository).updateBalance(walletId, betAmount);
    verify(betBookingRepository).completeBooking(betId, walletId);
  }

  @DisplayName("bet loser should only complete bet because amount was already deducted from wallet balance")
  @Test
  void betLoserShouldCompleteWithoutUpdatingBalanceSuccessful() {

    final var walletId = 1L;
    final var betAmount = -100L;
    final var betId = 1L;
    final var betResult = CustomerBetResult.loser(betId, walletId, betAmount);

    doNothing().when(betBookingRepository).completeBooking(betId, walletId);

    betProcessable.processCustomerBet(betResult);

    verify(walletRepository, never()).updateBalance(anyLong(), anyLong());
    verify(betBookingRepository).completeBooking(betId, walletId);
  }
}