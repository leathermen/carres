package com.nikitades.carres.application.reservation;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nikitades.carres.application.UuidProvider;
import com.nikitades.carres.application.exception.BadRequestException;
import com.nikitades.carres.application.exception.NotFoundException;
import com.nikitades.carres.domain.Car;
import com.nikitades.carres.domain.CarRepository;
import com.nikitades.carres.domain.Notifier;
import com.nikitades.carres.domain.Reservation;
import com.nikitades.carres.domain.ReservationRepository;
import com.nikitades.carres.infrastructure.java.JavaUuidProvider;
import com.nikitades.carres.infrastructure.prometheus.AnalyticsReporter;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReservationModuleTest {

  @Mock
  ReservationRepository reservationRepository;

  @Mock
  CarRepository carRepository;

  @Mock
  AnalyticsReporter analyticsReporter;

  @Mock
  Notifier notifier;

  UuidProvider uuidProvider = new JavaUuidProvider();

  ReservationModule instance;

  @BeforeEach
  void init() {
    instance =
      new ReservationModule(
        reservationRepository,
        carRepository,
        uuidProvider,
        analyticsReporter,
        notifier
      );
  }

  @Test
  void whenMissingCarIdIsPassed_thenNotFoundExceptionIsThrown() {
    //Given than no cars are ever found
    when(carRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

    //when new reservation request is made, there's an exception
    assertThrows(
      NotFoundException.class,
      () ->
        instance.createReservation(
          UUID.randomUUID(),
          "nikitades@pm.me",
          UUID.randomUUID(),
          Instant.now(),
          30
        )
    );
  }

  @Test
  void whenTooShortReservationDurationIsPassed_thenBadRequestExceptionIsThrown() {
    //Given that there are some cars in the database
    Car car = Instancio.of(Car.class).create();
    when(carRepository.findById(any(UUID.class))).thenReturn(Optional.of(car));

    //when a too short duration is passed, there's an exception
    assertThrows(
      BadRequestException.class,
      () ->
        instance.createReservation(
          UUID.randomUUID(),
          "nikitades@pm.me",
          UUID.randomUUID(),
          Instant.now(),
          10
        )
    );
  }

  @Test
  void whenReservationIsMadeSoonerThanACertainTimeFromNow_thenBadRequestExceptionIsThrown() {
    //Given that there are some cars in the database
    Car car = Instancio.of(Car.class).create();
    when(carRepository.findById(any(UUID.class))).thenReturn(Optional.of(car));

    //when a too close date is passed, there's an exception
    assertThrows(
      BadRequestException.class,
      () ->
        instance.createReservation(
          UUID.randomUUID(),
          "nikitades@pm.me",
          UUID.randomUUID(),
          Instant.now().plus(Duration.ofMinutes(5)),
          30
        )
    );
  }

  @Test
  void whenOtherReservationsOverlapWithNewlyCreatedOne_thenBadRequestExceptionIsThrown() {
    //Given that there are some cars in the database
    Car car = Instancio.of(Car.class).create();
    when(carRepository.findById(any(UUID.class))).thenReturn(Optional.of(car));

    //and some reservations exist in the database too
    Reservation reservation1 = Instancio
      .of(Reservation.class)
      .set(
        field(Reservation::getStartsAt),
        LocalDateTime
          .now()
          .plus(Duration.ofDays(1))
          .withHour(15)
          .withMinute(0)
          .toInstant(ZoneOffset.UTC)
      )
      .set(
        field(Reservation::getEndsAt),
        LocalDateTime
          .now()
          .plus(Duration.ofDays(1))
          .withHour(15)
          .withMinute(30)
          .toInstant(ZoneOffset.UTC)
      )
      .create();
    when(reservationRepository.findByCarId(any(UUID.class))).thenReturn(List.of(reservation1));

    //when a reservation yet to create is overlapping with the existing one, there's an exception
    assertThrows(
      BadRequestException.class,
      () -> {
        instance.createReservation(
          UUID.randomUUID(),
          "nikitades@pm.me",
          UUID.randomUUID(),
          LocalDateTime
            .now()
            .plus(Duration.ofDays(1))
            .withHour(15)
            .withMinute(15)
            .toInstant(ZoneOffset.UTC),
          15
        );
      }
    );
  }

  @Test
  void whenReservationSuccessfullyCreated_thenAnalyticsCounterIncremented() {
    //Given that there are some cars in the database
    Car car = Instancio.of(Car.class).create();
    when(carRepository.findById(any(UUID.class))).thenReturn(Optional.of(car));

    //when a reservation is created
    instance.createReservation(
      UUID.randomUUID(),
      "nikitades@pm.me",
      UUID.randomUUID(),
      LocalDateTime
        .now()
        .plus(Duration.ofDays(1))
        .withHour(15)
        .withMinute(15)
        .toInstant(ZoneOffset.UTC),
      15
    );

    //analytics method is surely called
    verify(analyticsReporter, times(1))
      .addReservationCreated(car.getModel(), car.getManufacturer());

    //notification method is called as well
    verify(notifier, times(1))
      .notifyOfNewReservation("nikitades@pm.me", car.getManufacturer(), car.getModel());
  }
}
