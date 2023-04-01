package com.nikitades.carres.entrypoint.controller.reservation;

import static org.instancio.Select.field;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nikitades.carres.domain.Car;
import com.nikitades.carres.domain.CarRepository;
import com.nikitades.carres.domain.Reservation;
import com.nikitades.carres.domain.ReservationRepository;
import com.nikitades.carres.entrypoint.mock.security.MockJwt;
import com.nikitades.carres.entrypoint.mock.security.Users;
import com.nikitades.carres.entrypoint.mock.security.WithMockJwt;
import jakarta.transaction.Transactional;
import org.instancio.Instancio;
import org.instancio.Model;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ReservationRepository reservationRepository;

  @Autowired
  private CarRepository carRepository;

  @Test
  @Transactional
  @WithMockJwt(MockJwt.Nikita)
  void whenReservationListRequested_reservationsAreShown() throws Exception {
    //Given some car exists in the database
    Model<Car> carModel = Instancio
      .of(Car.class)
      .set(field(Car::getCreatedBy), Users.Nikita.getId())
      .toModel();

    Car car = carRepository.save(Instancio.of(carModel).create());

    //and also that there are reservations for these vehicles
    Model<Reservation> reservationModel = Instancio
      .of(Reservation.class)
      .set(field(Reservation::getOwnerId), Users.Nikita.getId())
      .set(field(Reservation::getCar), car)
      .toModel();

    reservationRepository.save(Instancio.of(reservationModel).create());
    reservationRepository.save(Instancio.of(reservationModel).create());
    reservationRepository.save(Instancio.of(reservationModel).create());

    //when someone authorized requests the reservations list
    var actual = mvc.perform(get("/api/v1/reservations"));

    //then the list is there, and of a proper items count
    actual
      .andExpect(status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.items").exists())
      .andExpect(MockMvcResultMatchers.jsonPath("$.items.length()").value(3));
  }
}
