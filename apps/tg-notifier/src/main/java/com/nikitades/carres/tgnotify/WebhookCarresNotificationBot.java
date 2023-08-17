package com.nikitades.carres.tgnotify;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class WebhookCarresNotificationBot extends TelegramWebhookBot {

  private final String token;
  private final String name;

  public WebhookCarresNotificationBot(
    @Value("${bot.token}") String token,
    @Value("${bot.name}") String name,
    @Value("${bot.webhookAddress}") String webhookAddress
  ) throws TelegramApiException {
    super(token);
    this.token = token;
    this.name = name;
    setWebhook(new SetWebhook(webhookAddress));
  }

  public String getBotUsername() {
    return name;
  }

  @Override
  public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {
      return new SendMessage(
        update.getMessage().getChatId().toString(),
        update.getMessage().getText()
      );
    }

    return null;
  }

  @Override
  public String getBotPath() {
    return "/%s".formatted(token);
  }
}
