package com.nikitades.carres.infrastructure.spring.security;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
          .requestMatchers("/actuator/**", "/open/**", "/api/*/open/**", "/v3/api-docs/**")
          .permitAll();
        requests.anyRequest().authenticated();
      })
      .oauth2ResourceServer(oauth2 -> oauth2.jwt());

    return http.build();
  }
}
