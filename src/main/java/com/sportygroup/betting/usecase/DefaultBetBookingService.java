package com.sportygroup.betting.usecase;

import com.sportygroup.betting.api.FormulaOneEventResultRequest;
import com.sportygroup.betting.api.PlaceBetRequest;
import com.sportygroup.betting.infrastructure.database.BetBooking;
import com.sportygroup.betting.infrastructure.database.BetBookingRepository;
import com.sportygroup.betting.infrastructure.database.WalletRepository;
import java.time.OffsetDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultBetBookingService implements BetBookingService {

  private final BetBookingRepository betBookingRepository;
  private final WalletRepository walletRepository;

  public DefaultBetBookingService(final BetBookingRepository betBookingRepository, final WalletRepository walletRepository) {
    this.betBookingRepository = betBookingRepository;
    this.walletRepository = walletRepository;
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
  @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
  public void completeRace(final FormulaOneEventResultRequest eventResult) {
    //to make this scalable, the bet-booking table should have been partitioned by e.g., country and
    //the result should be paginated.
    //final var betsForEvent = betBookingRepository.findByEventId(eventResult.eventId());
  }
}
