package com.nikitades.carres.tgnotify;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class TgnotifyApplicationTests {

  @MockBean
  private WebhookCarresNotificationBot bot;

  @Test
  void contextLoads() {}
}
