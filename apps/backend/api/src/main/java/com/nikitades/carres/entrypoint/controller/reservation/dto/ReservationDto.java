package com.nikitades.carres.entrypoint.controller.reservation.dto;

import com.nikitades.carres.domain.Reservation;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class ReservationDto {

  private final UUID id;
  private final Instant startsAt;
  private final Instant endsAt;
  private final String vehicleDescription;
  private final boolean isCancelled;

  public static ReservationDto fromReservation(Reservation reservation) {
    return new ReservationDto(
      reservation.getId(),
      reservation.getStartsAt(),
      reservation.getEndsAt(),
      String.format(
        "%s %s",
        reservation.getCar().getManufacturer(),
        reservation.getCar().getModel()
      ),
      reservation.isCancelled()
    );
  }
}
