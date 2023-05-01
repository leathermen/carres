package com.nikitades.carres.infrastructure.keycloak;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "keycloak.admin.oauth")
@Getter
@Setter
public class KeycloakAuthProperties {

  private String realm;
  private String host;
  private String clientId;
  private String clientSecret;
}
