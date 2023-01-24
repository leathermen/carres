package com.nikitades.carres.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Reservation {

  @Id
  private UUID id;

  @ManyToOne
  private Car car;

  @Column
  private Date startsAt;

  @Column
  private Date endsAt;

  @Column
  private String clientEmail;

  @Column
  private Date createdAt;

  @Column
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
