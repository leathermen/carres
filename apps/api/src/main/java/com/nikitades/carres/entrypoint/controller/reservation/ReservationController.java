package com.nikitades.carres.entrypoint.controller.reservation;

import com.nikitades.carres.application.reservation.ReservationModule;
import com.nikitades.carres.entrypoint.controller.reservation.dto.ReservationDto;
import com.nikitades.carres.entrypoint.controller.reservation.dto.ReservationsResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationModule reservationModule;

  @GetMapping
  public ReservationsResponse getReservations(JwtAuthenticationToken token) {
    return new ReservationsResponse(
      reservationModule
        .getReservations(UUID.fromString(token.getName()))
        .stream()
        .map(reservation ->
          new ReservationDto(
            reservation.getId(),
            reservation.getStartsAt(),
            reservation.getEndsAt(),
            String.format(
              "%s %s",
              reservation.getCar().getManufacturer(),
              reservation.getCar().getModel()
            )
          )
        )
        .toList()
    );
  }
}
