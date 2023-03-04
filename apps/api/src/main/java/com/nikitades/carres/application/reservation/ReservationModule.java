package com.nikitades.carres.application.reservation;

import com.nikitades.carres.domain.Reservation;
import com.nikitades.carres.domain.ReservationRepository;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReservationModule {

  private final ReservationRepository reservationRepository;

  public List<Reservation> getReservations(UUID userId) {
    return reservationRepository.findByOwnerId(userId);
  }
}
