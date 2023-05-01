package com.nikitades.carres.entrypoint.controller.dashboard.dto;

import com.nikitades.carres.domain.Reservation;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class ReservationDto {

  private final UUID id;
  private final Instant startsAt;
  private final Instant endsAt;
  private final boolean isCancelled;
  private final String vehicleDescription;

  public static ReservationDto fromReservation(Reservation reservation) {
    return new ReservationDto(
      reservation.getId(),
      reservation.getStartsAt(),
      reservation.getEndsAt(),
      reservation.isCancelled(),
      String.format(
        "%s %s",
        reservation.getCar().getManufacturer(),
        reservation.getCar().getModel()
      )
    );
  }
}
