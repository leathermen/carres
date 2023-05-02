package com.nikitades.carres.application.reservation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nikitades.carres.application.dashboard.DashboardModule;
import com.nikitades.carres.application.exception.NotFoundException;
import com.nikitades.carres.application.sso.SsoClient;
import com.nikitades.carres.application.sso.dto.UserDto;
import com.nikitades.carres.domain.Reservation;
import com.nikitades.carres.domain.ReservationRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class DashboardModuleTest {

  @Mock
  private ReservationRepository reservationRepository;

  @Mock
  private SsoClient ssoClient;

  private DashboardModule instance;

  @BeforeEach
  void init() {
    instance = new DashboardModule(reservationRepository, ssoClient);
  }

  @Test
  void whenGetUserReservationsIsCalled_aProperlyPaginatedRequestIsSent() {
    //given that the user id is known
    UUID userId = UUID.fromString("b46d999f-d4b2-46ed-9ab9-955e9b287b40");
    //when a call is made
    instance.getUserReservations(userId, 5);

    //then the pagination has been correctly created
    verify(reservationRepository, times(1))
      .findByOwnerIdOrderByStartsAtDesc(userId, PageRequest.of(5, 10));
  }

  @Test
  void whenGetUserReservationsTotalPagesIsCalled_mathIsCorrect() {
    //given that there is exactly 26 reservations made by a person
    when(reservationRepository.countByOwnerId(any(UUID.class))).thenReturn(26);
    //so there are two full pages and one partial
    assertEquals(3, instance.getUserReservationsTotalPages(UUID.randomUUID()));

    //given that there is exactly 0 reservation made by a person
    when(reservationRepository.countByOwnerId(any(UUID.class))).thenReturn(0);
    //so there is 0 pages
    assertEquals(0, instance.getUserReservationsTotalPages(UUID.randomUUID()));

    //given that there is exactly 1 reservation made by a person
    when(reservationRepository.countByOwnerId(any(UUID.class))).thenReturn(1);
    //so there is only 1 page
    assertEquals(1, instance.getUserReservationsTotalPages(UUID.randomUUID()));

    //given that there is 10 reservations made by a person
    when(reservationRepository.countByOwnerId(any(UUID.class))).thenReturn(10);
    //so there still only 1 page
    assertEquals(1, instance.getUserReservationsTotalPages(UUID.randomUUID()));
  }

  @Test
  void whenSearchingUserByEmail_andUserIsThere_noExceptionIsThrown() {
    //given that the user is found by certain email
    when(ssoClient.findUserByEmail("email@example.com"))
      .thenReturn(
        Optional.of(
          new UserDto(UUID.fromString("87202758-978b-44d6-9932-19cc4c9ca528"), "email@example.com")
        )
      );

    //when the search is made, no exceptions raise
    assertDoesNotThrow(() -> instance.findUserByEmail("email@example.com"));
  }

  @Test
  void whenSearchingUserByEmail_andNoUsersAreFound_exceptionIsThrown() {
    //given that the user is found by certain email
    when(ssoClient.findUserByEmail(any(String.class))).thenReturn(Optional.empty());

    //when the search is made, no exceptions raise
    assertThrows(NotFoundException.class, () -> instance.findUserByEmail("email@example.com"));
  }

  @Test
  void whenCancellingReservation_andNoReservationFound_exceptionIsThrown() {
    //given that no reservations are ever found
    when(reservationRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

    //when the cancel attempt is made, the exception is thrown
    assertThrows(NotFoundException.class, () -> instance.cancelReservation(UUID.randomUUID()));
  }

  @Test
  void whenCancellingReservation_andReservationIsThere_reservationIsSavedWithReversedCancelledFlag() {
    //given that the reservation is on place
    Reservation reservation = mock(Reservation.class);
    when(reservationRepository.findById(any(UUID.class))).thenReturn(Optional.of(reservation));

    //when the cancel attempt is made
    instance.cancelReservation(UUID.randomUUID());

    //the reservation is set to cancelled
    verify(reservation, times(1)).setCancelled(true);

    //and then saved
    verify(reservationRepository, times(1)).save(reservation);
  }
}
