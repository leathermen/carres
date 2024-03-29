package com.nikitades.carres.infrastructure.keycloak;

import lombok.AllArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@AllArgsConstructor
@Profile("!test")
public class KeycloakClientConfig {

  private final KeycloakAuthProperties keycloakProperties;

  @Bean
  public Keycloak keycloak() {
    return KeycloakBuilder
      .builder()
      .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
      .clientId(keycloakProperties.getClientId())
      .clientSecret(keycloakProperties.getClientSecret())
      .serverUrl(keycloakProperties.getHost())
      .realm(keycloakProperties.getRealm())
      .scope("openid")
      .build();
  }
}
