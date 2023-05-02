package com.nikitades.carres.entrypoint.controller.dashboard;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nikitades.carres.domain.Car;
import com.nikitades.carres.domain.CarRepository;
import com.nikitades.carres.domain.Reservation;
import com.nikitades.carres.domain.ReservationRepository;
import com.nikitades.carres.entrypoint.mock.security.MockJwt;
import com.nikitades.carres.entrypoint.mock.security.Users;
import com.nikitades.carres.entrypoint.mock.security.WithMockJwt;
import jakarta.transaction.Transactional;
import java.util.List;
import org.instancio.Instancio;
import org.instancio.Model;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
class DashboardControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private CarRepository carRepository;

  @Autowired
  private ReservationRepository reservationRepository;

  @MockBean
  private Keycloak keycloak;

  @Test
  @Transactional
  @WithMockJwt(MockJwt.Thomas)
  void whenUnauthorizedUserRequestsTheList_heGetsNothing() throws Exception {
    //so, when the unprivileged user requests the user's reservations list
    var actual = mvc.perform(
      get(
        String.format(
          "/api/v1/dashboard/user-reservations?userId=%s&page=0",
          Users.Chuck.getId().toString()
        )
      )
    );

    //the response is forbidden
    actual.andExpect(status().isForbidden());
  }

  @Test
  @Transactional
  @WithMockJwt(MockJwt.Nikita)
  void whenUserReservationsListIsRequested_reservationsAreShown() throws Exception {
    //Given that some cars exist by the moment of the request
    Model<Car> carModel = Instancio
      .of(Car.class)
      .set(field(Car::getCreatedBy), Users.Nikita.getId())
      .toModel();

    Car car1 = carRepository.save(Instancio.of(carModel).create());
    Car car2 = carRepository.save(Instancio.of(carModel).create());
    Car car3 = carRepository.save(Instancio.of(carModel).create());

    //and some user has managed to create some reservations for these cars
    Model<Reservation> reservationModel = Instancio
      .of(Reservation.class)
      .set(field(Reservation::getOwnerId), Users.Keanu.getId())
      .toModel();

    //15, precisely
    for (int i = 0; i < 5; i++) {
      reservationRepository.save(
        Instancio.of(reservationModel).set(field(Reservation::getCar), car1).create()
      );
      reservationRepository.save(
        Instancio.of(reservationModel).set(field(Reservation::getCar), car2).create()
      );
      reservationRepository.save(
        Instancio.of(reservationModel).set(field(Reservation::getCar), car3).create()
      );
    }

    //when the authorized manager requests the user's reservations list
    var page1 = mvc.perform(
      get(
        String.format(
          "/api/v1/dashboard/user-reservations?userId=%s&page=0",
          Users.Keanu.getId().toString()
        )
      )
    );

    //then the list is there
    page1.andExpect(status().isOk());

    //and items amount is not more than allowed (10)
    page1.andExpect(jsonPath("$.items.length()").value(10));

    //and the pages count is valid
    page1.andExpect(jsonPath("$.totalPages").value(2));

    //and when the next page is requested
    var page2 = mvc.perform(
      get(
        String.format(
          "/api/v1/dashboard/user-reservations?userId=%s&page=1",
          Users.Keanu.getId().toString()
        )
      )
    );

    //then the list is there
    page2.andExpect(status().isOk());

    //and items amount just equal to the reminder of the list (5)
    page2.andExpect(jsonPath("$.items.length()").value(5));

    //and the pages count is still valid
    page2.andExpect(jsonPath("$.totalPages").value(2));
  }

  @Test
  @Transactional
  @WithMockJwt(MockJwt.Nikita)
  void whenExistingUserIsSoughtByEmail_heIsFound() throws Exception {
    //Given that there is a user in the SSO system
    UserRepresentation fakeUser = mock(UserRepresentation.class);
    when(fakeUser.getId()).thenReturn("bedcf0e6-392e-4858-8720-80f5d1ec097e");
    when(fakeUser.getEmail()).thenReturn("email@example.com");

    List<UserRepresentation> users = List.of(fakeUser);

    UsersResource fakeUsers = mock(UsersResource.class);
    when(fakeUsers.searchByEmail(eq("email@example.com"), any(Boolean.class))).thenReturn(users);

    RealmResource fakeRealm = mock(RealmResource.class);
    when(fakeRealm.users()).thenReturn(fakeUsers);

    when(keycloak.realm(any())).thenReturn(fakeRealm);

    //So when a request for a user with a certain email is made
    var actual = mvc.perform(get("/api/v1/dashboard/user?email=email@example.com"));

    //there's a correct response
    actual.andExpect(status().isOk());

    //and the user is found
    actual
      .andExpect(jsonPath("$.id").value("bedcf0e6-392e-4858-8720-80f5d1ec097e"))
      .andExpect(jsonPath("$.email").value("email@example.com"));
  }

  @Test
  @Transactional
  @WithMockJwt(MockJwt.Nikita)
  void whenNonExistentUserIsSoughtByEmail_notFoundIsGiven() throws Exception {
    //Given that there are no users in the realm yet
    List<UserRepresentation> users = List.of();

    UsersResource fakeUsers = mock(UsersResource.class);
    when(fakeUsers.searchByEmail(eq("email@example.com"), any(Boolean.class))).thenReturn(users);

    RealmResource fakeRealm = mock(RealmResource.class);
    when(fakeRealm.users()).thenReturn(fakeUsers);

    when(keycloak.realm(any())).thenReturn(fakeRealm);

    //when a request is made
    var actual = mvc.perform(get("/api/v1/dashboard/user?email=email@example.com"));

    //the network status is 404
    actual.andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  @WithMockJwt(MockJwt.Nikita)
  void whenSomeoneCancelsAReservation_itsCancelled() throws Exception {
    //Given that there's a reservation for a car in the database
    Car car = carRepository.save(
      Instancio.of(Car.class).set(field(Car::getCreatedBy), Users.Nikita.getId()).create()
    );

    Reservation reservation = reservationRepository.save(
      Instancio
        .of(Reservation.class)
        .set(field(Reservation::getOwnerId), Users.Keanu.getId())
        .set(field(Reservation::getCar), car)
        .set(field(Reservation::isCancelled), false)
        .create()
    );

    //when an authorized user makes a request to cancel the reservation
    var actual = mvc.perform(
      delete(String.format("/api/v1/dashboard/user-reservations/%s", reservation.getId()))
    );

    //the response status is correct
    actual.andExpect(status().isOk());

    //and the reservation has become cancelled
    Reservation updatedReservation = reservationRepository.findById(reservation.getId()).get();
    assertTrue(updatedReservation.isCancelled());
  }
}
