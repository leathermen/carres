package com.nikitades.carres.domain;

import java.util.List;

public interface CarRepository {
  List<Car> findByAvailableIsTrue();

  Car save(Car car);
}
