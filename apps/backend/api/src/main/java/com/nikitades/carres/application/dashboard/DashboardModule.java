package com.nikitades.carres.application.dashboard;

import com.nikitades.carres.application.exception.NotFoundException;
import com.nikitades.carres.application.sso.SsoClient;
import com.nikitades.carres.application.sso.dto.UserDto;
import com.nikitades.carres.domain.Reservation;
import com.nikitades.carres.domain.ReservationRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DashboardModule {

  private static final int LIMIT = 10;

  private final ReservationRepository reservationRepository;
  private final SsoClient ssoClient;

  public List<Reservation> getUserReservations(UUID userId, int page) {
    return reservationRepository.findByOwnerIdOrderByStartsAtDesc(
      userId,
      PageRequest.of(page, LIMIT)
    );
  }

  public int getUserReservationsTotalPages(UUID userId) {
    return (int) Math.ceil(reservationRepository.countByOwnerId(userId) / (float) LIMIT);
  }

  public UserDto findUserByEmail(String email) {
    Optional<UserDto> user = ssoClient.findUserByEmail(email);

    return user.orElseThrow(() -> new NotFoundException("User is not found", "USER_NOT_FOUND"));
  }

  public void cancelReservation(UUID reservationId) {
    Optional<Reservation> reservation = reservationRepository.findById(reservationId);

    if (reservation.isEmpty()) {
      throw new NotFoundException("Reservation is not found", "RESERVATION_NOT_FOUND");
    }

    reservation.get().setCancelled(true);
    reservationRepository.save(reservation.get());
  }
}
