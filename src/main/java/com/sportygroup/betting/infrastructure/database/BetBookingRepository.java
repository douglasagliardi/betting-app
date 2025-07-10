package com.sportygroup.betting.infrastructure.database;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BetBookingRepository extends JpaRepository<BetBooking, Long> {

  Collection<BetBooking> findByEventId(Long eventId);

  @Modifying
  @Query("UPDATE BetBooking SET completed = true WHERE walletId = :walletId")
  void completeBooking(@Param("walletId") long walletId);
}
