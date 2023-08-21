package com.nikitades.carres.domain;

import com.nikitades.carres.domain.exception.BadReservationDurationException;
import com.nikitades.carres.domain.exception.CannotReserveVehicleForTooSoonException;
import com.nikitades.carres.domain.exception.ReservationOverlapsWithAnotherOneException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
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

  public static Reservation createWithNoOverlapping(
    List<Reservation> allPossiblyConcurrentReservations,
    UUID id,
    UUID ownerId,
    Car car,
    Instant startsAt,
    int durationMinutes,
    Instant createdAt,
    Instant now
  )
    throws BadReservationDurationException, ReservationOverlapsWithAnotherOneException, CannotReserveVehicleForTooSoonException {
    Reservation newReservation = new Reservation(
      id,
      ownerId,
      car,
      startsAt,
      durationMinutes,
      createdAt
    );

    if (startsAt.isBefore(now.plus(Duration.ofHours(1)))) {
      throw new CannotReserveVehicleForTooSoonException(
        "New reservation must be at least in 1 hour from now"
      );
    }

    if (durationMinutes < 15) {
      throw new BadReservationDurationException("Minimal reservation duration is 15 minutes.");
    }

    for (Reservation existingReservation : allPossiblyConcurrentReservations) {
      if (checkIfOverlaps(newReservation, existingReservation)) {
        throw new ReservationOverlapsWithAnotherOneException(
          String.format(
            "New reservation overlaps with existing one <%s> by time",
            existingReservation.getId()
          )
        );
      }
    }

    return newReservation;
  }

  private Reservation(
    UUID id,
    UUID ownerId,
    Car car,
    Instant startsAt,
    int durationMinutes,
    Instant createdAt
  ) {
    this.id = id;
    this.ownerId = ownerId;
    this.car = car;
    this.startsAt = startsAt;
    this.endsAt = Instant.ofEpochSecond(startsAt.getEpochSecond() + (durationMinutes * 60));
    this.createdAt = createdAt;
    this.cancelled = false;
  }

  private static boolean checkIfOverlaps(
    Reservation newReservation,
    Reservation existingReservation
  ) {
    Instant existingReservationStartWithTimePadding = existingReservation
      .getStartsAt()
      .minus(Duration.ofMinutes(10));

    Instant existingReservationEndWithTimePadding = existingReservation
      .getEndsAt()
      .plus(Duration.ofMinutes(10));

    if (
      newReservation.getStartsAt().isAfter(existingReservationStartWithTimePadding) &&
      newReservation.getStartsAt().isBefore(existingReservationEndWithTimePadding)
    ) {
      return true;
    }

    if (
      newReservation.getEndsAt().isAfter(existingReservationStartWithTimePadding) &&
      newReservation.getEndsAt().isBefore(existingReservationEndWithTimePadding)
    ) {
      return true;
    }

    return false;
  }
}
