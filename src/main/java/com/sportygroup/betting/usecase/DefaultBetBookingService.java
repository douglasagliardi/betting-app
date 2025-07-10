package com.sportygroup.betting.usecase;

import com.sportygroup.betting.domain.CreateBetBooking;
import com.sportygroup.betting.infrastructure.database.BetBooking;
import com.sportygroup.betting.infrastructure.database.BetBookingRepository;
import com.sportygroup.betting.infrastructure.database.WalletRepository;
import java.math.BigDecimal;
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
  public void create(final CreateBetBooking booking) {
    final var bet = new BetBooking();
    bet.setUserId(booking.userId());
    bet.setAmount(booking.amount());
    bet.setEventId(booking.eventId());
    bet.setOdd(new BigDecimal(booking.odd()));
    walletRepository.updateBalance(booking.userId(), booking.amount());
    betBookingRepository.save(bet);
  }
}
