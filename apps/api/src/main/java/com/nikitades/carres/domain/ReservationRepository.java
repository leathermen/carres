package com.nikitades.carres.domain;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository {
  List<Reservation> findAll();

  List<Reservation> findByCarId(UUID carId);

  List<Reservation> findByOwnerIdOrderByStartsAtDesc(UUID ownerId);

  Reservation save(Reservation reservation);
}
