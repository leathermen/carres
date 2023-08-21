package com.nikitades.carres.domain;

import java.time.Instant;

public interface TimeProvider {
  public Instant utcNow();
}
