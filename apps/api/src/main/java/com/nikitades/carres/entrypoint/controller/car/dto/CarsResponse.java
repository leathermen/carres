package com.nikitades.carres.entrypoint.controller.car.dto;

import java.util.List;
import lombok.Data;

@Data
public class CarsResponse {

  private final List<CarDto> items;
}
