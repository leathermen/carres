package com.nikitades.carres.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarRepository {
  List<Car> findByAvailableIsTrue();

  Optional<Car> findById(UUID carId);

  Car save(Car car);
}
