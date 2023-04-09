package com.nikitades.carres.domain;

import com.nikitades.carres.domain.Exception.BadReservationDurationException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reservations")
public class Reservation {

  @Id
  private UUID id;

  @Column
  private UUID ownerId;

  @ManyToOne(optional = false)
  private Car car;

  @Column
  private Instant startsAt;

  @Column
  private Instant endsAt;

  @Column
  private Instant createdAt;

  @Column
  private boolean cancelled;

  private Reservation() {}

  public Reservation(
    UUID id,
    UUID ownerId,
    Car car,
    Instant startsAt,
    int durationMinutes,
    Instant createdAt
  ) throws BadReservationDurationException {
    if (durationMinutes < 15) {
      throw new BadReservationDurationException("Minimal reservation duration is 15 minutes.");
    }
    this.id = id;
    this.ownerId = ownerId;
    this.car = car;
    this.startsAt = startsAt;
    this.endsAt = Instant.ofEpochMilli(startsAt.getEpochSecond() + durationMinutes * 60000);
    this.createdAt = createdAt;
    this.cancelled = false;
  }
}
