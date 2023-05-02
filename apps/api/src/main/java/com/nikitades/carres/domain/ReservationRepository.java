package com.nikitades.carres.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.PageRequest;

public interface ReservationRepository {
  List<Reservation> findAll();

  List<Reservation> findByCarId(UUID carId);

  List<Reservation> findByOwnerIdOrderByStartsAtDesc(UUID ownerId);

  List<Reservation> findByOwnerIdOrderByStartsAtDesc(UUID ownerId, PageRequest pageRequest);

  int countByOwnerId(UUID ownerId);

  Optional<Reservation> findById(UUID reservationId);

  Reservation save(Reservation reservation);
}
