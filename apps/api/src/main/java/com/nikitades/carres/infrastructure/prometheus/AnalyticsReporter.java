package com.nikitades.carres.infrastructure.prometheus;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsReporter {

  private final String RESERVATIONS_CREATED = "carres_api_reservations_created";

  public AnalyticsReporter(MeterRegistry registry) {
    Counter.builder(RESERVATIONS_CREATED).tags("model", "", "manufacturer").register(registry);
  }

  public void addReservationCreated(String model, String manufacturer) {
    Metrics.counter(RESERVATIONS_CREATED, "model", model, "manufacturer", manufacturer).increment();
  }
}
