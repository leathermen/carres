package com.nikitades.carres.application.reservation;

import com.nikitades.carres.domain.Reservation;
import com.nikitades.carres.domain.ReservationRepository;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ReservationModule {

  private final ReservationRepository reservationRepository;

  public List<Reservation> getReservations() {
    return reservationRepository.findAll();
  }
}
