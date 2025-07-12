package com.sportygroup.betting.usecase;

import com.sportygroup.betting.api.FormulaOneEventResultRequest;
import com.sportygroup.betting.api.PlaceBetRequest;
import com.sportygroup.betting.domain.CustomerBetResult;
import com.sportygroup.betting.infrastructure.database.BetBooking;
import com.sportygroup.betting.infrastructure.database.BetBookingRepository;
import com.sportygroup.betting.infrastructure.database.WalletRepository;
import com.sportygroup.betting.infrastructure.message.QueueProperties;
import java.time.OffsetDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultBetBookingService implements BetBookingService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultBetBookingService.class);

  private final AmqpTemplate messageTemplate;
  private final BetBookingRepository betBookingRepository;
  private final WalletRepository walletRepository;
  private final QueueProperties queueProperties;

  public DefaultBetBookingService(
      final AmqpTemplate messageTemplate,
      final BetBookingRepository betBookingRepository,
      final WalletRepository walletRepository,
      final QueueProperties queueProperties
  ) {
    this.betBookingRepository = betBookingRepository;
    this.walletRepository = walletRepository;
    this.messageTemplate = messageTemplate;
    this.queueProperties = queueProperties;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
  public BookedBet create(final PlaceBetRequest request) {
    final var bet = new BetBooking();
    bet.setWalletId(request.walletId());
    bet.setAmount(request.amount());
    bet.setEventId(request.eventId());
    bet.setDriverId(request.driverId());
    bet.setOdd(request.odd());
    bet.setCreatedAt(OffsetDateTime.now());
    if (walletRepository.updateBalance(request.walletId(), bet.getAmount() * -1) > 0) {
      final var entity = betBookingRepository.save(bet);
      return new BookedBet(entity.getId());
    }
    throw new InsufficientFundsException(request.walletId(), request.amount());
  }

  @Override
  public void completeRace(final FormulaOneEventResultRequest eventResult) {
    final var winnerFrom = getWinner(eventResult);
    final var betsForEvent = betBookingRepository.findByEventId(eventResult.eventId());
    if (betsForEvent.isEmpty()) {
      LOGGER.atInfo().setMessage("Unable to find any bets for event '{}'.").addArgument(eventResult.eventId()).log();
    }
    betsForEvent.forEach(bet -> messageTemplate.convertAndSend(queueProperties.queue(), getBetResultWith(winnerFrom, bet)));
  }

  private CustomerBetResult getBetResultWith(final FormulaOneEventResult f1Result, final BetBooking booking) {
    if (f1Result.driverId() == booking.getDriverId()) {
      return new CustomerBetResult(booking.getId(), booking.getWalletId(), booking.getAmount() * booking.getOdd(), true);
    }
    return new CustomerBetResult(booking.getId(), booking.getWalletId(), booking.getAmount() * -1, false);
  }

  private FormulaOneEventResult getWinner(final FormulaOneEventResultRequest request) {
    return request.placements()
        .stream()
        .filter(e -> e.position() == 1) //finds winner
        .findFirst()
        .map(winner -> new FormulaOneEventResult(request.eventId(), winner.driverId()))
        .orElseThrow(() -> new NotEventWinnerFoundException(request.eventId()));
  }
}
