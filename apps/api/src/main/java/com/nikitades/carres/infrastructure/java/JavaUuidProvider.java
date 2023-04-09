package com.nikitades.carres.infrastructure.java;

import com.nikitades.carres.application.UuidProvider;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class JavaUuidProvider implements UuidProvider {

  @Override
  public UUID provide() {
    return UUID.randomUUID();
  }
}
