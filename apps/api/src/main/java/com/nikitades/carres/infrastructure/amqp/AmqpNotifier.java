package com.nikitades.carres.infrastructure.amqp;

import com.nikitades.carres.domain.Notifier;
import com.nikitades.carres.shared.NewReservationNotification;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmqpNotifier implements Notifier {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void notifyOfNewReservation(String email, String manufacturer, String model) {
    rabbitTemplate.convertAndSend(
      "new_reservations",
      new NewReservationNotification(email, manufacturer, model)
    );
  }
}
