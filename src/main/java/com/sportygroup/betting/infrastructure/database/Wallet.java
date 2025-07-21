package com.sportygroup.betting.infrastructure.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Wallet extends BaseEntity {

  @Id
  @GeneratedValue(
      generator = "wallet_id_seq",
      strategy = GenerationType.SEQUENCE
  )
  @SequenceGenerator(
      name = "wallet_id_seq",
      allocationSize = 1
  )
  private long id;
  private long balance;
  private long userId;

  public Wallet() {
  }

  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public long getBalance() {
    return balance;
  }

  public void setBalance(final long balance) {
    this.balance = balance;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(final long userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "Wallet{" +
        "id=" + id +
        ", balance=" + balance +
        '}';
  }
}
