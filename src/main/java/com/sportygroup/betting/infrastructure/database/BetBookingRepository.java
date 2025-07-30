package com.sportygroup.betting.infrastructure.database;

import java.util.Collection;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BetBookingRepository extends JpaRepository<BetBooking, Long> {

  @Query("SELECT count(id) FROM BetBooking WHERE eventId = :eventId and completed = false")
  int countByEventId(long eventId);

  @Query("SELECT b.completed FROM BetBooking b WHERE b.id = :betId and b.completed = false")
  boolean isBetBookingOpen(long betId);

  @Query("SELECT b FROM BetBooking b WHERE b.eventId = :eventId AND b.completed = false")
  Collection<BetBooking> findByEventId(final Page page, long eventId);

  @Modifying
  @Query("UPDATE BetBooking SET completed = true WHERE id = :betId AND walletId = :walletId")
  void completeBooking(@Param("betId") long betId, @Param("walletId") long walletId);
}
