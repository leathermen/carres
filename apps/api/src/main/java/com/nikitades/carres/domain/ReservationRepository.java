package com.nikitades.carres.domain;

import java.util.List;

public interface ReservationRepository {
  List<Reservation> findAll();
}
