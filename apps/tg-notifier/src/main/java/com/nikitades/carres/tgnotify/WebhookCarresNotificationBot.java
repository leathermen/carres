package com.nikitades.carres.tgnotify;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class WebhookCarresNotificationBot extends TelegramWebhookBot {

  private final String token;
  private final String name;
  private final String chatId;

  public WebhookCarresNotificationBot(
    @Value("${bot.token}") String token,
    @Value("${bot.name}") String name,
    @Value("${bot.chat-id}") String chatId
  ) {
    super(token);
    this.token = token;
    this.name = name;
    this.chatId = chatId;
  }

  public String getBotUsername() {
    return name;
  }

  @Override
  public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
    throw new NotImplementedException("Not likely to ever be implemented");
  }

  @Override
  public String getBotPath() {
    return "/%s".formatted(token);
  }

  public void notify(String message) throws TelegramApiException {
    execute(new SendMessage(chatId, message));
  }
}
