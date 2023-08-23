package com.nikitades.carres.infrastructure.spring.security;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc)
    throws Exception {
    http
      .cors(Customizer.withDefaults()) //for h2
      .headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin)) //for h2
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(requests -> {
        requests
          .requestMatchers(antMatcher("/h2-console/**"))
          .permitAll()
          .requestMatchers(mvc.pattern("/api/*/dashboard/**"))
          .hasAnyAuthority("manager")
          .requestMatchers(
            mvc.pattern("/open/**"),
            mvc.pattern("/api/*/open/**"),
            mvc.pattern("/v3/api-docs/**")
          )
          .permitAll()
          .requestMatchers(mvc.pattern("/actuator/health"))
          .permitAll()
          .requestMatchers(mvc.pattern("/actuator/prometheus"))
          .hasAnyAuthority("metrics-scraper");
        requests.anyRequest().authenticated();
      })
      .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

    return http.build();
  }

  @Scope("prototype")
  @Bean
  MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
    return new MvcRequestMatcher.Builder(introspector);
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
