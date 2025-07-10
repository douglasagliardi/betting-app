package com.sportygroup.betting.infrastructure.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

  @Modifying
  @Query("UPDATE Wallet w SET w.balance = w.balance + :amount WHERE w.userId = :userId")
  void updateBalance(@Param("userId") long userId, @Param("amount") long amount);
}
