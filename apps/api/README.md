# Cars reservation - API

## Swagger

**[https://api.carres.nikitades.com/open/swagger-ui](https://api.carres.nikitades.com/open/swagger-ui)**

This is the backend part of the "Cars reservation" project. Since this so-called service is stateful, I need some mechanism to control how the project states shift each other.
I decided to go the most straightforward way: to put all the encapsulated logic into the regular backend app. It's going to handle authentication issues and data management.

## Stack & ideas

It's Spring 3.0.2, Hibernate and Keycloak (embedded). Users are authenticated by provided JWT. The session is stateless. The app is built with the idea of distributed apps in mind. So each app's replica is replaceable and can handle requests in parallel with other ones. 12-factor app idea was considered at the time of the creation of this application.