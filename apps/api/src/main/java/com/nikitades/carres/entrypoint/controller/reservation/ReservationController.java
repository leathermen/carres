package com.nikitades.carres.entrypoint.controller.reservation;

import com.nikitades.carres.application.reservation.ReservationModule;
import com.nikitades.carres.entrypoint.controller.reservation.dto.ReservationDto;
import com.nikitades.carres.entrypoint.controller.reservation.dto.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationModule reservationModule;

  @GetMapping
  public ReservationResponse getReservations() {
    return new ReservationResponse(
      reservationModule
        .getReservations()
        .stream()
        .map(reservation -> new ReservationDto(reservation.getId()))
        .toList()
    );
  }
}
