package com.nikitades.carres.domain;

public interface Notifier {
  void notifyOfNewReservation(String email, String model, String manufacturer);
}
