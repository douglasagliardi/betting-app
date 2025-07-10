package com.sportygroup.betting.infrastructure.database;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetBookingRepository extends JpaRepository<BetBooking, Long> {

  Collection<BetBooking> findByEventId(Long eventId);
}
