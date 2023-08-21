package com.nikitades.carres.infrastructure.java;

import com.nikitades.carres.domain.TimeProvider;
import java.time.Instant;
import org.springframework.stereotype.Component;

@Component
public class JavaTimeProvider implements TimeProvider {

  public Instant utcNow() {
    return Instant.now();
  }
}
