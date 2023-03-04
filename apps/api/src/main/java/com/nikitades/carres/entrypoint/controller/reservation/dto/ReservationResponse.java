package com.nikitades.carres.entrypoint.controller.reservation.dto;

import java.util.List;
import lombok.Data;

@Data
public class ReservationResponse {

  private final List<ReservationDto> items;
}
