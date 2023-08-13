package com.nikitades.carres.infrastructure.spring.security;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .cors()
      .and()
      .csrf()
      .disable()
      .headers()
      .frameOptions()
      .disable()
      .and()
      .authorizeHttpRequests(requests -> {
        requests
          .requestMatchers(antMatcher("/h2-console/**"))
          .permitAll()
          .requestMatchers("/api/*/dashboard/**")
          .hasAnyAuthority("manager")
          .requestMatchers("/open/**", "/api/*/open/**", "/v3/api-docs/**")
          .permitAll()
          .requestMatchers("/actuator/health")
          .permitAll()
          .requestMatchers("/actuator/prometheus")
          .hasAnyAuthority("metrics-scraper");
        requests.anyRequest().authenticated();
      })
      .oauth2ResourceServer(oauth2 ->
        oauth2.jwt().jwtAuthenticationConverter(authenticationConverter())
      );

    return http.build();
  }

  protected JwtAuthenticationConverter authenticationConverter() {
    JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
    authoritiesConverter.setAuthorityPrefix("");
    authoritiesConverter.setAuthoritiesClaimName("roles");

    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
    return converter;
  }
}
