package com.nikitades.carres.infrastructure.spring.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
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
