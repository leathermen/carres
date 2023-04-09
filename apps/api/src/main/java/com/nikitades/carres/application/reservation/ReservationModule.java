package com.nikitades.carres.application.reservation;

import com.nikitades.carres.application.UuidProvider;
import com.nikitades.carres.application.exception.BadRequestException;
import com.nikitades.carres.application.exception.NotFoundException;
import com.nikitades.carres.domain.Car;
import com.nikitades.carres.domain.CarRepository;
import com.nikitades.carres.domain.Reservation;
import com.nikitades.carres.domain.ReservationRepository;
import com.nikitades.carres.domain.exception.BadReservationDurationException;
import com.nikitades.carres.domain.exception.CannotReserveVehicleForTooSoonException;
import com.nikitades.carres.domain.exception.ReservationOverlapsWithAnotherOneException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationModule {

  private final ReservationRepository reservationRepository;
  private final CarRepository carRepository;
  private final UuidProvider uuidProvider;

  public List<Reservation> getReservations(UUID userId) {
    return reservationRepository.findByOwnerIdOrderByStartsAtDesc(userId);
  }

  public Reservation createReservation(
    UUID userId,
    UUID vehicleId,
    Instant startTime,
    int durationMinutes
  ) {
    Optional<Car> car = carRepository.findById(vehicleId);

    if (!car.isPresent()) {
      throw new NotFoundException("Car is not found", "NO_CAR_FOUND");
    }

    List<Reservation> possiblyOverlappingReservations = reservationRepository.findByCarId(
      vehicleId
    );

    Reservation reservation;

    try {
      reservation =
        Reservation.createWithNoOverlapping(
          possiblyOverlappingReservations,
          uuidProvider.provide(),
          userId,
          car.get(),
          startTime,
          durationMinutes,
          startTime
        );
    } catch (BadReservationDurationException e) {
      throw new BadRequestException(e.getMessage(), "BAD_RESERVATION_DURATION");
    } catch (ReservationOverlapsWithAnotherOneException e) {
      throw new BadRequestException(e.getMessage(), "RESERVATION_OVERLAPS_WITH_EXISTING_ONE");
    } catch (CannotReserveVehicleForTooSoonException e) {
      throw new BadRequestException(e.getMessage(), "CANNOT_RESERVE_FOR_TOO_SOON");
    }

    reservationRepository.save(reservation);

    return reservation;
  }
}
