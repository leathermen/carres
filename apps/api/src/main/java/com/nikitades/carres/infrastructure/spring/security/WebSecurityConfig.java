package com.nikitades.carres.infrastructure.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@Configuration
public class WebSecurityConfig {

  @Bean
  public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
    http
      .cors()
      .and()
      .csrf()
      .disable()
      .authorizeExchange(requests -> {
        requests
          .pathMatchers("/actuator/**", "/open/**", "/api/*/open/**", "/v3/api-docs/**")
          .permitAll();
        requests.anyExchange().authenticated();
      })
      .oauth2ResourceServer(oauth2 -> oauth2.jwt());

    return http.build();
  }
}
