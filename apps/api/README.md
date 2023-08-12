# Cars Reservation System - API

## Swagger

**[https://api.carres.nikitades.com/open/swagger-ui](https://cars-reservation-api.nikitades.com/open/swagger-ui)**

This is the backend part of the "Cars Reservation System" project. Since this so-called service is stateful, I need some mechanism to control how the project states shift each other.
I decided to go the most straightforward way: to put all the encapsulated logic into the regular backend app. It's going to handle authentication issues and data management.

## Stack & ideas

It's Spring 3.0.2 and Hibernate. Users are authenticated by provided JWT. The auth provider is the Keycloak server. The session is stateless. The app is built with the idea of distributed apps in mind. So each app's replica is replaceable and can handle requests in parallel with other ones. The 12-factor app idea was considered at the time of the creation of this application.