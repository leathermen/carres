package com.nikitades.carres.entrypoint.mock.security;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.JwtClaimNames;

@AllArgsConstructor
@Getter
public class User {

  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private List<JwtRole> roles;
  private String token;
  private List<String> scope;

  public Map<String, Object> getClaims() {
    return Map.of(
      JwtClaimNames.SUB,
      id.toString(),
      StandardClaimNames.GIVEN_NAME,
      firstName,
      StandardClaimNames.FAMILY_NAME,
      lastName,
      StandardClaimNames.NAME,
      String.format("%s %s", firstName, lastName),
      StandardClaimNames.EMAIL,
      email,
      "roles",
      roles
    );
  }
}
