package com.nikitades.carres.tgnotify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
@EnableScheduling
public class TgnotifyApplication {

  @Autowired
  private WebhookCarresNotificationBot bot;

  public static void main(String[] args) throws TelegramApiException {
    SpringApplication.run(TgnotifyApplication.class);
  }
}
