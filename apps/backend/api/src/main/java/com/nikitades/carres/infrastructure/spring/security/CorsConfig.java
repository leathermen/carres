package com.nikitades.carres.infrastructure.spring.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Profile("prod")
@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
      .addMapping("/**")
      .allowedOriginPatterns("https://carres.nikitades.com")
      .allowedMethods("*");
  }
}
