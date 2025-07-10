package com.sportygroup.betting.usecase;

import com.sportygroup.betting.api.FormulaOneEventResultRequest;
import com.sportygroup.betting.api.PlaceBetRequest;
import com.sportygroup.betting.domain.CustomerBetResult;
import com.sportygroup.betting.infrastructure.database.BetBooking;
import com.sportygroup.betting.infrastructure.database.BetBookingRepository;
import com.sportygroup.betting.infrastructure.database.WalletRepository;
import java.time.OffsetDateTime;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultBetBookingService implements BetBookingService {

  private final AmqpTemplate messageTemplate;
  private final BetBookingRepository betBookingRepository;
  private final WalletRepository walletRepository;

  public DefaultBetBookingService(
      final AmqpTemplate messageTemplate,
      final BetBookingRepository betBookingRepository,
      final WalletRepository walletRepository
  ) {
    this.betBookingRepository = betBookingRepository;
    this.walletRepository = walletRepository;
    this.messageTemplate = messageTemplate;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
  public BookedBet create(final PlaceBetRequest booking) {
    final var bet = new BetBooking();
    bet.setWalletId(booking.walletId());
    bet.setAmount(booking.amount());
    bet.setEventId(booking.eventId());
    bet.setOdd(booking.odd());
    bet.setCreatedAt(OffsetDateTime.now());
    if (walletRepository.updateBalance(booking.walletId(), bet.getAmount() * -1) > 0) {
      final var entity = betBookingRepository.save(bet);
      return new BookedBet(entity.getId());
    }
    throw new InsufficientFundsException();
  }

  @Override
  public void completeRace(final FormulaOneEventResultRequest eventResult) {
    final var winnerFrom = getWinner(eventResult);
    final var betsForEvent = betBookingRepository.findByEventId(eventResult.eventId());
    betsForEvent.forEach(bet -> messageTemplate.convertAndSend("formulaone-bets", getBetResultWith(winnerFrom, bet)));
  }

  private CustomerBetResult getBetResultWith(final FormulaOneEventResult f1Result, final BetBooking booking) {
    if (f1Result.driverId() == booking.getPlayerId()) {
      return new CustomerBetResult(booking.getWalletId(), calculateAmount(booking), true);
    }
    return new CustomerBetResult(booking.getWalletId(), calculateAmount(booking) * -1, false);
  }

  private long calculateAmount(final BetBooking booking) {
    return booking.getAmount() * booking.getOdd();
  }

  private FormulaOneEventResult getWinner(final FormulaOneEventResultRequest request) {
    return request.placements()
        .stream()
        .filter(e -> e.position() == 1) //finds winner
        .findFirst()
        .map(winner -> new FormulaOneEventResult(request.eventId(), winner.driverId()))
        .orElseThrow();
  }
}
