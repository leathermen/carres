package com.nikitades.carres.infrastructure.spring.data.jpa;

import com.nikitades.carres.domain.Reservation;
import com.nikitades.carres.domain.ReservationRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReservationRepository
  extends JpaRepository<Reservation, UUID>, ReservationRepository {}
