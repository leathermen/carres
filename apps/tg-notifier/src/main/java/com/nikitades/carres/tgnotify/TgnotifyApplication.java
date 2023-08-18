package com.nikitades.carres.tgnotify;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.nikitades.carres.shared.NewReservationNotification;

@SpringBootApplication
@EnableRabbit
public class TgnotifyApplication {

  @Autowired
  private WebhookCarresNotificationBot bot;

  public static void main(String[] args) {
    SpringApplication.run(TgnotifyApplication.class);
  }

  @RabbitListener(queues = "new_reservations")
  public void receive(NewReservationNotification msg) throws TelegramApiException {
    bot.notify(
      "New reservation:%n%s%n%s%n%s".formatted(msg.email(), msg.manufacturer(), msg.model())
    );
  }
}
