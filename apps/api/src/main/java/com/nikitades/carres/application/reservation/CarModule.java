package com.nikitades.carres.application.reservation;

import com.nikitades.carres.domain.Car;
import com.nikitades.carres.domain.CarRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CarModule {

  private final CarRepository carRepository;

  public List<Car> getAvailableCars() {
    return carRepository.findByAvailableIsTrue();
  }
}
