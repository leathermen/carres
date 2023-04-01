package com.nikitades.carres.infrastructure.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPISecurityConfiguration {

  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String oidcIssuerUri;

  @Bean
  public OpenAPI defaultOpenApi() {
    return new OpenAPI()
      .components(
        new Components()
          .addSecuritySchemes(
            "OIDC",
            new SecurityScheme()
              .name("Keycloak OIDC")
              .type(SecurityScheme.Type.OPENIDCONNECT)
              .openIdConnectUrl(oidcIssuerUri + "/.well-known/openid-configuration")
              .scheme("bearer")
          )
      )
      .addSecurityItem(new SecurityRequirement().addList("OIDC"))
      .info(
        new Info()
          .title("Cars Reservation API")
          .version("v1.0.0")
          .description("Reserve cars and see existing reservations")
      );
  }
}
