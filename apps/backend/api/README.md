# Cars Reservation System - API

## Swagger

**[https://api.carres.nikitades.com/open/swagger-ui](https://cars-reservation-api.nikitades.com/open/swagger-ui)**

This is the backend part of the "Cars Reservation System" project. Since this so-called service is stateful, I need some mechanism to control how the project states shift each other.
I decided to go the most straightforward way: to put all the encapsulated logic into the regular backend app. It's going to handle authentication issues and data management.

## Stack & ideas

It's Spring 3.1.2 and Hibernate. Users are authenticated by provided JWT. The auth provider is the Keycloak server. The session is stateless. The app is built with the idea of distributed apps in mind. So each app's replica is replaceable and can handle requests in parallel with other ones. The 12-factor app idea was considered at the time of the creation of this application.

The project is divided into 4 layers: Entrypoint, Application, Domain and Infrastructure.

Entrypoint: the HTTP receiving layer, with all the code that helps to express certain expectations from an HTTP request (validation, methods, authentication annotations and such)

Application: the orchestration method. Roughly speaking, all the code that is deeper than the entrypoint but not the business rules yet. Basically, application layer is responsible for calling certain domain invariant operations.

Domain: where the business rules live. Minimum items in order? No clients younger than 18 years old? Time reservation conflicts should not be possible? All of it is Domain. Domain depends on nothing.

Infrastructure: a box of wires. The most low-level code that puts together various technologies and program parts. Specific implementations of agnostic repositories also belong to here. Also, Spring configs live here.

[More about my grasp on the software architecture >>](../../../ARCHITECTURE.md)

## Hibernate

Every ORM brings the trade-off of permormance. To a certain point, it's purely possible to have both code that's easy to write and to maintain and not to witness major permormance spikes. But on a level, there's some sense in getting rid of the ORM and switching to some lower level way of making queries and hydrating data. [jOOQ](https://github.com/jOOQ) is a nice way to go then.