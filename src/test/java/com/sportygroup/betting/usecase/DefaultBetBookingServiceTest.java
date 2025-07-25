package com.sportygroup.betting.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sportygroup.betting.api.FormulaOneEventResultRequest;
import com.sportygroup.betting.api.FormulaOneRacer;
import com.sportygroup.betting.api.PlaceBetRequest;
import com.sportygroup.betting.domain.CustomerBetResult;
import com.sportygroup.betting.infrastructure.database.BetBooking;
import com.sportygroup.betting.infrastructure.database.BetBookingRepository;
import com.sportygroup.betting.infrastructure.database.WalletRepository;
import com.sportygroup.betting.infrastructure.message.QueueProperties;
import java.security.SecureRandom;
import java.util.List;
import java.util.stream.IntStream;
import org.hibernate.query.Page;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;

@ExtendWith(MockitoExtension.class)
class DefaultBetBookingServiceTest {

  @Mock
  private AmqpTemplate messageTemplate;
  @Mock
  private BetBookingRepository betBookingRepository;
  @Mock
  private WalletRepository walletRepository;
  @Mock
  private QueueProperties queueProperties;
  @InjectMocks
  private DefaultBetBookingService betBookingService;

  @DisplayName("creating a bet using wallet with funds should book it successfully")
  @Test
  void creatingBetUsingWalletWithSufficientFundsShouldSucceed() {
    final var walletId = 1L;
    final var amount = 50L;
    final var amountToDeduct = -50L;

    final var betRequest = new PlaceBetRequest(1L, walletId, 1, amount, 2);

    final var betId = new SecureRandom().nextLong();
    final var dbBooking = new BetBooking();
    dbBooking.setId(betId);

    final var betBookingArgCaptor = ArgumentCaptor.forClass(BetBooking.class);

    when(walletRepository.updateBalance(walletId, amountToDeduct))
        .thenReturn(1); //1-> successful operation (1 row changed)
    when(betBookingRepository.save(betBookingArgCaptor.capture()))
        .thenReturn(dbBooking); //persisting bet

    final var result = betBookingService.create(betRequest);
    assertNotNull(result);
    assertEquals(betId, result.betId());
    assertEquals(amount, betBookingArgCaptor.getValue().getAmount());

    verify(walletRepository).updateBalance(walletId, amountToDeduct);
    verify(betBookingRepository).save(any());
  }

  @DisplayName("creating a bet using wallet **without** funds should fail")
  @Test
  void creatingBetUsingWalletWithoutFundsShouldThrowException() {

    final var walletId = 1L;
    final var amount = 50L;
    final var amountToDeduct = -50L;

    final var betRequest = new PlaceBetRequest(1L, walletId, 1, amount, 2);

    when(walletRepository.updateBalance(walletId, amountToDeduct))
        .thenReturn(0); // -> 0 rows changed

    assertThrows(UnableToPlaceBetException.class, () -> betBookingService.create(betRequest));

    verify(walletRepository).updateBalance(walletId, amountToDeduct);
    verify(betBookingRepository, never()).save(any());
  }

  @DisplayName("receiving event completion with winners and losers should create one event for each with matching values (e.g; amount")
  @Test
  void completeBetContainingWinnerShouldEmitEventSuccessfully() {

    final var request = new FormulaOneEventResultRequest(1, List.of(
        new FormulaOneRacer(3, 3),
        new FormulaOneRacer(2, 2),
        new FormulaOneRacer(1, 1)
    ));

    final var betOne = new BetBooking();
    betOne.setId(1L);
    betOne.setDriverId(1);
    betOne.setAmount(50L);
    betOne.setOdd(2);

    final var betTwo = new BetBooking();
    betTwo.setId(2L);
    betTwo.setDriverId(2);
    betTwo.setAmount(25L);
    betTwo.setOdd(3);

    final var queueName = "formulaone-bets";
    when(queueProperties.queue()).thenReturn(queueName);
    when(betBookingRepository.countByEventId(1L)).thenReturn(2);
    when(betBookingRepository.findByEventId(any(Page.class), eq(1L))).thenReturn(List.of(betOne, betTwo));

    final var messageArgCaptor = ArgumentCaptor.forClass(CustomerBetResult.class);
    doNothing().when(messageTemplate).convertAndSend(eq(queueName), messageArgCaptor.capture());

    betBookingService.completeRace(request);

    verify(betBookingRepository).countByEventId(1L);
    verify(betBookingRepository).findByEventId(any(Page.class), eq(1L));
    verify(messageTemplate, times(2)).convertAndSend(eq(queueName), any(CustomerBetResult.class));

    assertEquals(2, messageArgCaptor.getAllValues().size());
    final var firstEvent = messageArgCaptor.getAllValues().getFirst();
    assertEquals(100, firstEvent.amount()); //amount * odd => 50 * 2 = 100
    assertTrue(firstEvent.succeeded());

    final var secondEvent = messageArgCaptor.getAllValues().getLast();
    assertEquals(-25, secondEvent.amount());
    assertFalse(secondEvent.succeeded());
  }

  @DisplayName("receiving event completion with winners and losers should create one event for each with matching values (e.g; amount")
  @Test
  void receiving100BetsForEventShouldPaginate5Times() {

    final var firstBatch = IntStream.range(1, 21).mapToObj(this::fakeBet).toList();
    final var secondBatch = IntStream.range(21, 41).mapToObj(this::fakeBet).toList();
    final var thirdBatch = IntStream.range(41, 61).mapToObj(this::fakeBet).toList();
    final var fourthBatch = IntStream.range(61, 81).mapToObj(this::fakeBet).toList();
    final var fifthBatch = IntStream.range(81, 101).mapToObj(this::fakeBet).toList();

    final var request = new FormulaOneEventResultRequest(1, List.of(
        new FormulaOneRacer(3, 3),
        new FormulaOneRacer(2, 2),
        new FormulaOneRacer(1, 1)
    ));

    final var queueName = "formulaone-bets";
    when(queueProperties.queue()).thenReturn(queueName);
    when(betBookingRepository.countByEventId(1L)).thenReturn(100);
    when(betBookingRepository.findByEventId(any(Page.class), eq(1L)))
        .thenReturn(firstBatch)
        .thenReturn(secondBatch)
        .thenReturn(thirdBatch)
        .thenReturn(fourthBatch)
        .thenReturn(fifthBatch);

    final var messageArgCaptor = ArgumentCaptor.forClass(CustomerBetResult.class);
    doNothing().when(messageTemplate).convertAndSend(eq(queueName), messageArgCaptor.capture());

    betBookingService.completeRace(request);

    verify(betBookingRepository).countByEventId(1L);
    verify(betBookingRepository, times(5)).findByEventId(any(Page.class), eq(1L));
    verify(messageTemplate, times(100)).convertAndSend(eq(queueName), any(CustomerBetResult.class));

    assertEquals(100, messageArgCaptor.getAllValues().size());
    assertEquals(50, messageArgCaptor.getAllValues().stream().filter(e -> e.amount() > 0).count()); //half has won
    assertEquals(50, messageArgCaptor.getAllValues().stream().filter(e -> e.amount() < 0).count()); //half has lost
  }

  private BetBooking fakeBet(final int id) {
    final var bet = new BetBooking();
    final var generator = new SecureRandom();
    bet.setId(id);
    bet.setOdd(generator.nextInt(2, 4));
    if (id % 2 == 0) {
      bet.setDriverId(1);
      bet.setAmount(50);
    } else {
      bet.setDriverId(2);
      bet.setAmount(10L);
    }
    return bet;
  }
}