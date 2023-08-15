package com.nikitades.carres.infrastructure.prometheus;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AnalyticsReporter {

  private static final String RESERVATIONS_CREATED = "carres_api_reservations_created";

  private final MeterRegistry registry;

  public AnalyticsReporter(MeterRegistry registry) {
    this.registry = registry;
    Counter.builder(RESERVATIONS_CREATED).tags("model", "", "manufacturer", "").register(registry);
  }

  public void addReservationCreated(String model, String manufacturer) {
    registry
      .counter(RESERVATIONS_CREATED, "model", model, "manufacturer", manufacturer)
      .increment();
  }
}
