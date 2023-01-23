package com.nikitades.carres.domain;

import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reservation {

  private UUID id;
  private Car car;
  private UUID carId;
  private Date startsAt;
  private Date endsAt;
  private String clientEmail;
  private Date createdAt;
  private boolean cancelled;

  public Reservation(
    UUID id,
    Car car,
    Date startsAt,
    int durationMinutes,
    String clientEmail,
    Date createdAt
  ) {
    if (durationMinutes < 15) {
      throw new IllegalArgumentException("Minimal reservation duration is 15 minutes.");
    }
    this.id = id;
    this.car = car;
    this.startsAt = startsAt;
    this.endsAt = new Date(startsAt.getTime() + durationMinutes * 60000);
    this.clientEmail = clientEmail;
    this.createdAt = createdAt;
    this.cancelled = false;
  }
}
