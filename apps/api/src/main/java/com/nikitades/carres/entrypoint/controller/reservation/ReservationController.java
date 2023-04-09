package com.nikitades.carres.entrypoint.controller.reservation;

import com.nikitades.carres.application.reservation.ReservationModule;
import com.nikitades.carres.domain.Reservation;
import com.nikitades.carres.entrypoint.controller.reservation.dto.CreateReservationRequest;
import com.nikitades.carres.entrypoint.controller.reservation.dto.ReservationDto;
import com.nikitades.carres.entrypoint.controller.reservation.dto.ReservationsListResponse;
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
  public ReservationsListResponse getReservations(JwtAuthenticationToken token) {
    return new ReservationsListResponse(
      reservationModule
        .getReservations(UUID.fromString(token.getName()))
        .stream()
        .map(ReservationDto::fromReservation)
        .toList()
    );
  }

  @PostMapping
  public ReservationDto createReservation(
    JwtAuthenticationToken token,
    @RequestBody CreateReservationRequest request
  ) {
    Reservation reservation = reservationModule.createReservation(
      UUID.fromString(token.getName()),
      request.getVehicleId(),
      request.getStartsAt(),
      request.getDurationMinutes()
    );

    return ReservationDto.fromReservation(reservation);
  }
}
