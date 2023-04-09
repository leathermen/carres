package com.nikitades.carres.application.reservation;

import com.nikitades.carres.domain.Car;
import com.nikitades.carres.domain.CarRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CarModule {

  private final CarRepository carRepository;

  public Optional<Car> findCar(UUID carId) {
    return carRepository.findById(carId);
  }

  public List<Car> getAvailableCars() {
    return carRepository.findByAvailableIsTrue();
  }
}
