package com.nikitades.carres.entrypoint.controller.car;

import static org.instancio.Select.field;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nikitades.carres.domain.Car;
import com.nikitades.carres.domain.CarRepository;
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

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {

  @Autowired
  private CarRepository carRepository;

  @Autowired
  private MockMvc mvc;

  @Test
  @Transactional
  @WithMockJwt(MockJwt.Nikita)
  void whenAvailableCarsListIsRequested_listIsShown() throws Exception {
    //Given that some cars exist in the database
    Model<Car> carModel = Instancio
      .of(Car.class)
      .set(field(Car::getCreatedBy), Users.Nikita.getId())
      .toModel();

    carRepository.save(Instancio.of(carModel).set(field(Car::isAvailable), true).create());
    carRepository.save(Instancio.of(carModel).set(field(Car::isAvailable), true).create());
    carRepository.save(Instancio.of(carModel).set(field(Car::isAvailable), true).create());
    carRepository.save(Instancio.of(carModel).set(field(Car::isAvailable), false).create());
    carRepository.save(Instancio.of(carModel).set(field(Car::isAvailable), false).create());

    //when someone authorized requests the available vehicles list
    var actual = mvc.perform(get("/api/v1/cars/available"));

    //then the list is there, and of a count of only 3 cars, as only 3 of 5 cars are available, as given
    actual
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.items").exists())
      .andExpect(jsonPath("$.items.length()").value(3));
  }

  @Test
  @Transactional
  @WithMockJwt(MockJwt.Nikita)
  void whenCarIsRequestedById_andCarIsPresentInDb_carIsShown() throws Exception {
    //Given that some cars exist in the database
    Car car = Instancio
      .of(Car.class)
      .set(field(Car::getCreatedBy), Users.Nikita.getId())
      .set(field(Car::isAvailable), true)
      .create();

    carRepository.save(car);

    //when an authorized user requests this car (e.g. at the time of the reservation)
    var actual = mvc.perform(get("/api/v1/cars/%s".formatted(car.getId())));

    //then the car is displayed in the regular fashion:
    actual
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(car.getId().toString()))
      .andExpect(jsonPath("$.manufacturer").value(car.getManufacturer()))
      .andExpect(jsonPath("$.model").value(car.getModel()))
      .andExpect(jsonPath("$.manufacturedAt").value(car.getManufacturedAt().toString()));
  }
}
