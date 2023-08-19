package com.nikitades.carres.infrastructure.spring.data.jpa;

import com.nikitades.carres.domain.Car;
import com.nikitades.carres.domain.CarRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCarRepository extends JpaRepository<Car, UUID>, CarRepository {}
