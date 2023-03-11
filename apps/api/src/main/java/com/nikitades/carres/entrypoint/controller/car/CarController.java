package com.nikitades.carres.entrypoint.controller.car;

import com.nikitades.carres.application.reservation.CarModule;
import com.nikitades.carres.entrypoint.controller.car.dto.CarDto;
import com.nikitades.carres.entrypoint.controller.car.dto.CarsResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cars")
@AllArgsConstructor
public class CarController {

  private final CarModule carModule;

  @GetMapping("/available")
  public CarsResponse getAvailableCars() {
    return new CarsResponse(
      carModule
        .getAvailableCars()
        .stream()
        .map(car ->
          new CarDto(car.getId(), car.getManufacturer(), car.getModel(), car.getManufacturedAt())
        )
        .toList()
    );
  }
}
