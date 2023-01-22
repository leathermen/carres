package com.nikitades.carres.infrastructure.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
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
      .authorizeHttpRequests(requests -> {
        requests
          .requestMatchers("/actuator/**", "/api/*/open/**", "/swagger-ui/**", "/v3/api-docs/**")
          .permitAll();
        requests.anyRequest().authenticated();
      })
      .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

    return http.build();
  }
}
