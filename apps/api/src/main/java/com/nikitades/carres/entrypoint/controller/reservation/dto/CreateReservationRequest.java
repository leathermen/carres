package com.nikitades.carres.entrypoint.controller.reservation.dto;

import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateReservationRequest {

  private Instant startsAt;
  private int durationMinutes;
  private UUID vehicleId;
}
