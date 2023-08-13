package com.nikitades.carres.entrypoint.controller.dashboard;

import com.nikitades.carres.application.dashboard.DashboardModule;
import com.nikitades.carres.entrypoint.controller.dashboard.dto.AdminReservationsListResponse;
import com.nikitades.carres.entrypoint.controller.dashboard.dto.ReservationDto;
import com.nikitades.carres.entrypoint.controller.dashboard.dto.UserDto;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@Slf4j
@AllArgsConstructor
public class DashboardController {

  private final DashboardModule dashboardModule;

  @GetMapping("/user-reservations")
  public AdminReservationsListResponse getUserReservations(
    @RequestParam UUID userId,
    @RequestParam int page
  ) {
    log.warn("User reservations requested");
    var reservations = dashboardModule.getUserReservations(userId, page);
    var totalPages = dashboardModule.getUserReservationsTotalPages(userId);
    return new AdminReservationsListResponse(
      reservations.stream().map(ReservationDto::fromReservation).toList(),
      totalPages
    );
  }

  @DeleteMapping("/user-reservations/{reservationId}")
  public void cancelReservation(@PathVariable UUID reservationId) {
    dashboardModule.cancelReservation(reservationId);
  }

  @GetMapping("/user")
  public UserDto getUserByEmail(@RequestParam String email) {
    com.nikitades.carres.application.sso.dto.UserDto user = dashboardModule.findUserByEmail(email);

    return new UserDto(user.id(), user.email());
  }
}
