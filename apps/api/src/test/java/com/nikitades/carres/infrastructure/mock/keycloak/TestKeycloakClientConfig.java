package com.nikitades.carres.infrastructure.mock.keycloak;

import org.instancio.Instancio;
import org.keycloak.admin.client.Keycloak;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestKeycloakClientConfig {

  @Bean
  public Keycloak keycloak() {
    return Instancio.of(Keycloak.class).create();
  }
}
