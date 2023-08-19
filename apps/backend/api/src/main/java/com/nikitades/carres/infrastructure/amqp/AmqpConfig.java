package com.nikitades.carres.infrastructure.amqp;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

  @Bean
  public MessageConverter converter() {
    return new Jackson2JsonMessageConverter();
  }
}
