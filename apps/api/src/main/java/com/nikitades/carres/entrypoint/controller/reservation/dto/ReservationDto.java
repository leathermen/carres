package com.nikitades.carres.entrypoint.controller.reservation.dto;

import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class ReservationDto {

  private final UUID id;
  private final Instant startsAt;
  private final Instant endsAt;
  private final String vehicleDescription;
}
