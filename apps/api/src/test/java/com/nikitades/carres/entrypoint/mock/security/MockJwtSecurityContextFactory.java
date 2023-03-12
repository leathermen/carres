package com.nikitades.carres.entrypoint.mock.security;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class MockJwtSecurityContextFactory implements WithSecurityContextFactory<WithMockJwt> {

  @Value("${spring.security.oauth2.resourceserver.jwt.audiences}")
  private String audience;

  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String issuer;

  @Override
  public SecurityContext createSecurityContext(WithMockJwt annotation) {
    if (!Users.registry.containsKey(annotation.value())) {
      throw new IllegalArgumentException("No such mocked user is available!");
    }

    var user = Users.registry.get(annotation.value());

    val jwt = Jwt
      .withTokenValue(user.getToken())
      .header("alg", "RS256")
      .header("typ", "Bearer")
      .header("kid", UUID.randomUUID().toString())
      .claim(JwtClaimNames.AUD, List.of(audience))
      .claim(JwtClaimNames.ISS, issuer)
      .claim(JwtClaimNames.EXP, Instant.now().plus(Duration.ofMinutes(5)))
      .claim(JwtClaimNames.IAT, Instant.now().minus(Duration.ofSeconds(30)))
      .claim(JwtClaimNames.JTI, UUID.randomUUID().toString())
      .claim(JwtClaimNames.SUB, user.getId().toString())
      .claim("email_verified", true)
      .claim("scope", String.join(" ", user.getScope()))
      .claim("name", String.format("%s %s", user.getFirstName(), user.getLastName()))
      .claim("preferred_username", user.getEmail())
      .claim("email", user.getEmail())
      .claim("given_name", user.getFirstName())
      .claim("family_name", user.getLastName())
      .build();

    val authorities = AuthorityUtils.createAuthorityList(
      user
        .getScope()
        .stream()
        .map(scope -> String.format("SCOPE_%s", scope))
        .toList()
        .toArray(new String[0])
    );
    val token = new JwtAuthenticationToken(jwt, authorities);

    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(token);
    return context;
  }
}
