package com.nikitades.carres.entrypoint.controller.car.dto;

import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class CarDto {

  private final UUID id;
  private final String manufacturer;
  private final String model;
  private final Instant manufacturedAt;
}
