package com.nikitades.carres.infrastructure.keycloak;

import com.nikitades.carres.application.sso.SsoClient;
import com.nikitades.carres.application.sso.dto.UserDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KeycloakSsoClient implements SsoClient {

  private final KeycloakAuthProperties keycloakProperties;
  private final Keycloak keycloak;

  @Override
  public Optional<UserDto> findUserByEmail(String email) {
    List<UserRepresentation> users = keycloak
      .realm(keycloakProperties.getRealm())
      .users()
      .searchByEmail(email, true);

    return users
      .stream()
      .map(userRepresentation ->
        new UserDto(UUID.fromString(userRepresentation.getId()), userRepresentation.getEmail())
      )
      .findFirst();
  }
}
