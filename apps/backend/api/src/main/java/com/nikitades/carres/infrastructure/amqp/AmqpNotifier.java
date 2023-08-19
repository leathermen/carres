package com.nikitades.carres.infrastructure.amqp;

import com.nikitades.carres.domain.Notifier;
import com.nikitades.carres.shared.NewReservationNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AmqpNotifier implements Notifier {

  private final RabbitTemplate rabbitTemplate;

  public void notifyOfNewReservation(String email, String manufacturer, String model) {
    rabbitTemplate.convertAndSend(
      "new_reservations",
      new NewReservationNotification(email, manufacturer, model)
    );
  }
}
