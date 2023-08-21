package com.nikitades.carres.domain;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.nikitades.carres.domain.exception.BadReservationDurationException;
import com.nikitades.carres.domain.exception.ReservationOverlapsWithAnotherOneException;
import com.nikitades.carres.infrastructure.java.JavaTimeProvider;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

class ReservationTest {

  private TimeProvider timeProvider = new JavaTimeProvider();

  @Test
  void whenReservationIsCreatedForLessThan15Minutes_thenExceptionIsThrown() {
    //Given that the desired time duration is less that 15 minutes
    int timeDurationMinutes = 14;
    //and some arbitrary car is used
    Car car = Instancio.of(Car.class).create();

    //when a new reservation is created, an exception is thrown
    assertThrows(
      BadReservationDurationException.class,
      () ->
        Reservation.createWithNoOverlapping(
          List.of(),
          UUID.randomUUID(),
          UUID.randomUUID(),
          car,
          timeProvider.utcNow().plus(Duration.ofDays(5)),
          timeDurationMinutes,
          timeProvider.utcNow(),
          timeProvider.utcNow()
        )
    );
  }

  @Test
  void whenReservationIsCreatedForMoreThan15Minutes_thenAllGoesSmoothly() {
    //Given that the desired time duration is more that 15 minutes
    int timeDurationMinutes = 16;
    //and some arbitrary car is used
    Car car = Instancio.of(Car.class).create();

    //when a new reservation is created, an exception is thrown
    Reservation actual = assertDoesNotThrow(() ->
      Reservation.createWithNoOverlapping(
        List.of(),
        UUID.randomUUID(),
        UUID.randomUUID(),
        car,
        timeProvider.utcNow().plus(Duration.ofDays(5)),
        timeDurationMinutes,
        timeProvider.utcNow(),
        timeProvider.utcNow()
      )
    );

    assertInstanceOf(Reservation.class, actual);
  }

  @Test
  void whenReservationExactlyOverlapsWithAnotherOne_thenExceptionIsThrown() {
    //Given that the time is current day at the noon
    Instant concurrentReservationTime = timeProvider
      .utcNow()
      .atZone(ZoneId.of("UTC"))
      .plus(Duration.ofDays(1))
      .withHour(12)
      .toInstant();

    //And that there is the reservation for that
    Reservation dummyReservation = Instancio
      .of(Reservation.class)
      .set(field(Reservation::getStartsAt), concurrentReservationTime)
      .set(field(Reservation::getEndsAt), concurrentReservationTime.plus(Duration.ofMinutes(30)))
      .create();

    //and some arbitrary car is used
    Car car = Instancio.of(Car.class).create();

    //so when a new reservation is created for exactly that time, an exception is thrown
    assertThrows(
      ReservationOverlapsWithAnotherOneException.class,
      () ->
        Reservation.createWithNoOverlapping(
          List.of(dummyReservation),
          UUID.randomUUID(),
          UUID.randomUUID(),
          car,
          concurrentReservationTime,
          30,
          timeProvider.utcNow(),
          timeProvider.utcNow()
        )
    );
  }

  @Test
  void whenReservationStartTimeOverlapsWithAnotherReservationSpan_thenExceptionIsThrown() {
    //Given that there is a reservation that lasts for 45 minutes from 14:00 till 14:45
    Instant concurrentReservationStartTime = timeProvider
      .utcNow()
      .atZone(ZoneId.of("UTC"))
      .plus(Duration.ofDays(1))
      .withHour(14)
      .withMinute(0)
      .toInstant();

    Instant concurrentReservationEndTime = timeProvider
      .utcNow()
      .atZone(ZoneId.of("UTC"))
      .plus(Duration.ofDays(1))
      .withHour(14)
      .withMinute(45)
      .toInstant();

    //And that there is the reservation for that
    Reservation dummyReservation = Instancio
      .of(Reservation.class)
      .set(field(Reservation::getStartsAt), concurrentReservationStartTime)
      .set(field(Reservation::getEndsAt), concurrentReservationEndTime)
      .create();

    //and some arbitrary car is used
    Car car = Instancio.of(Car.class).create();

    //so when a new reservation is created for exactly a span that starts within the used time, there's an exception

    Instant newReservationStartTime = timeProvider
      .utcNow()
      .atZone(ZoneId.of("UTC"))
      .plus(Duration.ofDays(1))
      .withHour(14)
      .withMinute(30)
      .toInstant();

    assertThrows(
      ReservationOverlapsWithAnotherOneException.class,
      () ->
        Reservation.createWithNoOverlapping(
          List.of(dummyReservation),
          UUID.randomUUID(),
          UUID.randomUUID(),
          car,
          newReservationStartTime,
          30,
          timeProvider.utcNow(),
          timeProvider.utcNow()
        )
    );
  }

  @Test
  void whenReservationEndTimeOverlapsWithAnotherReservationSpan_thenExceptionIsThrown() {
    //Given that there is a reservation that lasts for 45 minutes from 14:00 till 14:45
    Instant concurrentReservationStartTime = timeProvider
      .utcNow()
      .atZone(ZoneId.of("UTC"))
      .plus(Duration.ofDays(1))
      .withHour(14)
      .withMinute(0)
      .toInstant();

    Instant concurrentReservationEndTime = timeProvider
      .utcNow()
      .atZone(ZoneId.of("UTC"))
      .plus(Duration.ofDays(1))
      .withHour(14)
      .withMinute(45)
      .toInstant();

    //And that there is the reservation for that
    Reservation dummyReservation = Instancio
      .of(Reservation.class)
      .set(field(Reservation::getStartsAt), concurrentReservationStartTime)
      .set(field(Reservation::getEndsAt), concurrentReservationEndTime)
      .create();

    //and some arbitrary car is used
    Car car = Instancio.of(Car.class).create();

    //so when a new reservation is created for exactly a span that ends within the used time, there's an exception
    Instant newReservationStartTime = timeProvider
      .utcNow()
      .atZone(ZoneId.of("UTC"))
      .plus(Duration.ofDays(1))
      .withHour(13)
      .withMinute(45)
      .toInstant();

    assertThrows(
      ReservationOverlapsWithAnotherOneException.class,
      () ->
        Reservation.createWithNoOverlapping(
          List.of(dummyReservation),
          UUID.randomUUID(),
          UUID.randomUUID(),
          car,
          newReservationStartTime,
          45,
          timeProvider.utcNow(),
          timeProvider.utcNow()
        )
    );
  }

  @Test
  void whenReservationDoesNotOverlapWithAnotherOne_thenNoExceptions() {
    //Given that the times of reservation are different
    ZonedDateTime now = timeProvider.utcNow().atZone(ZoneId.of("UTC"));
    Instant time1 = now.withHour(12).toInstant();
    Instant time2 = now.withHour(16).toInstant().plus(Duration.ofDays(1));

    //And that there is the reservation for the first time
    Reservation dummyReservation = Instancio
      .of(Reservation.class)
      .set(field(Reservation::getStartsAt), time1)
      .set(field(Reservation::getEndsAt), time1.plus(Duration.ofMinutes(15)))
      .create();

    //and some arbitrary car is used
    Car car = Instancio.of(Car.class).create();

    //so when a new reservation is created for the 2nd time, no exceptions are thrown
    assertDoesNotThrow(() ->
      Reservation.createWithNoOverlapping(
        List.of(dummyReservation),
        UUID.randomUUID(),
        UUID.randomUUID(),
        car,
        time2,
        30,
        timeProvider.utcNow(),
        timeProvider.utcNow()
      )
    );
  }
}
