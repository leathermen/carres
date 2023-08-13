package com.nikitades.carres.infrastructure.prometheus;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Reporter {

  private final AtomicInteger testGauge;
  private final Counter testCounter;

  public Reporter(MeterRegistry registry) {
    testGauge = registry.gauge("some_gauge", new AtomicInteger(0));
    testCounter = registry.counter("some_counter");
  }

  @Scheduled(fixedRateString = "1000", initialDelayString = "0")
  public void schedulingTask() {
    log.info("increased");
    testGauge.set(35);
    testCounter.increment();
  }
}
