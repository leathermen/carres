# Cars Reservation System - Keycloak

This is a slightly tuned up image of Keycloak from [quay.io](https://quay.io/repository/keycloak/keycloak).

This app is supposed to be launched in the cloud, serving as a point of authentication and authorization across the application cluster of any size.

Currently, a single non-public client operates against this app: "carres-prod". You use this app each time you attend the authentication page of the [main application](https://cars-reservation-system.nikitades.com).

For the sake of the easier local development, a test realm dump is stored to [realm.json](./realm.json).