package com.sportygroup.betting.usecase;

import com.sportygroup.betting.api.FormulaOneEventResultRequest;
import com.sportygroup.betting.api.PlaceBetRequest;
import com.sportygroup.betting.domain.CustomerBetResult;
import com.sportygroup.betting.infrastructure.database.BetBooking;
import com.sportygroup.betting.infrastructure.database.BetBookingRepository;
import com.sportygroup.betting.infrastructure.database.WalletRepository;
import com.sportygroup.betting.infrastructure.message.QueueProperties;
import java.time.OffsetDateTime;
import org.hibernate.query.Page;
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

  private static final int PAGE_SIZE = 20;

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
    if (walletRepository.updateBalance(request.walletId(), negateAmount(bet)) > 0) {
      final var entity = betBookingRepository.save(bet);
      return new BookedBet(entity.getId());
    }
    throw new UnableToPlaceBetException(request.walletId(), request.amount());
  }

  private long negateAmount(final BetBooking bet) {
    return bet.getAmount() * -1;
  }

  @Override
  public void completeRace(final FormulaOneEventResultRequest eventResult) {
    final var winnerFrom = getWinner(eventResult);
    final var totalBetForEventId = betBookingRepository.countByEventId(eventResult.eventId());
    sliced(getPageSize(totalBetForEventId), eventResult, winnerFrom);
  }

  private int getPageSize(final float totalBetForEventId) {
    if (totalBetForEventId <= PAGE_SIZE) {
      return 1;
    }
    return Math.round(totalBetForEventId / PAGE_SIZE);
  }

  private void sliced(final int numberOfDbRequests, final FormulaOneEventResultRequest eventResult, final FormulaOneEventResult winner) {
    for (int index = 0; index < numberOfDbRequests; index++) {
      final var betsForEvent = betBookingRepository.findByEventId(Page.page(PAGE_SIZE, index), eventResult.eventId());
      if (betsForEvent.isEmpty()) {
        LOGGER.atInfo().setMessage("Unable to find any bets for event '{}'.").addArgument(eventResult.eventId()).log();
        return;
      }
      betsForEvent.forEach(bet -> messageTemplate.convertAndSend(queueProperties.queue(), getBetResultWith(winner, bet)));
    }
  }

  private CustomerBetResult getBetResultWith(final FormulaOneEventResult f1Result, final BetBooking booking) {
    if (f1Result.driverId() == booking.getDriverId()) {
      return CustomerBetResult.winner(booking.getId(), booking.getWalletId(), booking.getAmount() * booking.getOdd());
    }
    return CustomerBetResult.loser(booking.getId(), booking.getWalletId(), booking.getAmount());
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
