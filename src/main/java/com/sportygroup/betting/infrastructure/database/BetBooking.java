package com.sportygroup.betting.infrastructure.database;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import java.time.OffsetDateTime;

@jakarta.persistence.Entity
public class BetBooking {

  @Id
  @GeneratedValue(
      generator = "bet_booking_id_seq",
      strategy = GenerationType.SEQUENCE
  )
  @SequenceGenerator(
      name = "bet_booking_id_seq",
      allocationSize = 1
  )
  private long id;
  private long walletId;
  private long eventId;
  private int driverId;
  private long amount;
  private int odd;
  private OffsetDateTime createdAt;
  private boolean completed;

  public BetBooking() {
  }

  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public long getWalletId() {
    return walletId;
  }

  public void setWalletId(final long walletId) {
    this.walletId = walletId;
  }

  public long getEventId() {
    return eventId;
  }

  public void setEventId(final long eventId) {
    this.eventId = eventId;
  }

  public int getDriverId() {
    return driverId;
  }

  public void setDriverId(final int driverId) {
    this.driverId = driverId;
  }

  public long getAmount() {
    return amount;
  }

  public void setAmount(final long amount) {
    this.amount = amount;
  }

  public int getOdd() {
    return odd;
  }

  public void setOdd(final int odd) {
    this.odd = odd;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(final boolean completed) {
    this.completed = completed;
  }

  @Override
  public String toString() {
    return "BetBooking{" +
        "id=" + id +
        ", walletId=" + walletId +
        ", eventId=" + eventId +
        ", driverId=" + driverId +
        ", amount=" + amount +
        ", odd=" + odd +
        ", createdAt=" + createdAt +
        ", completed=" + completed +
        '}';
  }
}
