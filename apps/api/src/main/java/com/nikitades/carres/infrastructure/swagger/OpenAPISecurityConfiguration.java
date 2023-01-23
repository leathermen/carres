package com.nikitades.carres.infrastructure.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPISecurityConfiguration {

  @Bean
  public OpenAPI customizeOpenAPI() {
    final String securitySchemeName = "Bearer Authentication";
    return new OpenAPI()
      .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
      .info(
        new Info()
          .title("Cars Reservation API")
          .description(
            "Provides endpoints to reserve cars and see existing reservations, together with administrative interface"
          )
          .version("1.0.0")
      )
      .components(
        new Components()
          .addSecuritySchemes(
            securitySchemeName,
            new SecurityScheme()
              .name(securitySchemeName)
              .type(SecurityScheme.Type.HTTP)
              .scheme("bearer")
              .bearerFormat("JWT")
          )
      );
  }
}
