package com.nikitades.carres.infrastructure.amqp;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nikitades.carres.shared.NewReservationNotification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@ExtendWith(MockitoExtension.class)
class AmqpNotifierTest {

  private AmqpNotifier instance;

  @Mock
  private RabbitTemplate rabbitTemplate;

  @BeforeEach
  void init() {
    instance = new AmqpNotifier(rabbitTemplate);
  }

  @Test
  void whenNewReservationNotificationIsSent_correctQueueNameIsPassedAsRoutingKey() {
    //Given that nikitades@pm.me reserved Centaur Warrunner
    instance.notifyOfNewReservation("nikitades@pm.me", "Centaur", "Warrunner");

    //When a notification is performed
    verify(rabbitTemplate, times(1))
      .convertAndSend(
        "new_reservations",
        //correct data is passed
        new NewReservationNotification("nikitades@pm.me", "Centaur", "Warrunner")
      );
  }
}
