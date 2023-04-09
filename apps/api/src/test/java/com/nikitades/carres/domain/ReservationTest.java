package com.nikitades.carres.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.nikitades.carres.domain.Exception.BadReservationDurationException;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

class ReservationTest {

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
        new Reservation(
          UUID.randomUUID(),
          UUID.randomUUID(),
          car,
          Instant.now().plus(Duration.ofDays(5)),
          timeDurationMinutes,
          Instant.now()
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
      new Reservation(
        UUID.randomUUID(),
        UUID.randomUUID(),
        car,
        Instant.now().plus(Duration.ofDays(5)),
        timeDurationMinutes,
        Instant.now()
      )
    );

    assertInstanceOf(Reservation.class, actual);
  }
}
