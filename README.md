# Cars reservation

This is a test project. It's created to put together all the knowledge I have about creating web apps with Spring (and eventually also Javascript and some DevOps practiques).

## Applications and parts

- [Spring Boot API (readme)](apps/api/README.md)
- [React (Next.js) frontend app (readme)](apps/website/README.md)
- [Keycloak SSO server (readme)](apps/keycloak/README.md)
- [CloudFlare reverse proxying (readme)](REVERSE-PROXYING.md)

## Ideas

This whole project is expected to visualize the idea of the web application. The API is covered with tests thoroughly, both in unit and integration style. The domain area of the app is rather simple, so it takes no Ubiquitous Language to describe. Also, DDD techniques were intentionally not used to speed up the development and also out of lack of reason to use ones: the project is small, as I already said.

The project is hosted at Hetzner, using Kubernetes setup.
The Continuous Integration is done using GitHub actions.

DDOS mitigation is achieved by hiding the real IP address of the cluster entrypoints, by reverse proxying all the requests through CloudFlare system. It's known for being able to withstand attacks of a solid numbers of requests.

## Remarkable elements (please take a look at those)

- [API Swagger UI](https://cars-reservation-api.nikitades.com/open/swagger-ui)
- [**Public Sonarcloud report**](https://sonarcloud.io/summary/new_code?id=leathermen_carres_api) (the coverage included)
- [TODO public Grafana dashboard of technical and business metrics](somewhere)

## Credits

The author: Nikita Pavlovskiy, [nikitades@pm.me](nikitades@pm.me).

Contact me in Telegram: [@nikitades](https://t.me/nikitades).