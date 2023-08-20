# Cars Reservation System - Telegram Notifier

It is a small sidekick project with the only goal to send notifications to the Telegram channel of mine. It receives messages over the AMQP bus and performs the notification. API application produces messages on an occasion of event (for instance, when a new car reservation is made).

Spring Boot 3.0.2.