package com.sportygroup.betting.infrastructure.message;

import com.sportygroup.betting.domain.CustomerBetResult;
import com.sportygroup.betting.infrastructure.database.BetBookingRepository;
import com.sportygroup.betting.infrastructure.database.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BetBookingProcessable {

  private static final Logger LOGGER = LoggerFactory.getLogger(BetBookingProcessable.class);

  private final BetBookingRepository betBookingRepository;
  private final WalletRepository walletRepository;

  public BetBookingProcessable(final BetBookingRepository betBookingRepository, final WalletRepository walletRepository) {
    this.betBookingRepository = betBookingRepository;
    this.walletRepository = walletRepository;
  }

  @Transactional
  @RabbitListener(queues = {"${application.messaging.formulaone.bets.queue}"})
  public void processCustomerBet(final CustomerBetResult message) {
    LOGGER.atInfo().setMessage("Received Bet Booking Message from queue: '{}'").addArgument(message).log();
    walletRepository.updateBalance(message.walletId(), message.amount());
    betBookingRepository.completeBooking(message.walletId());
  }
}
