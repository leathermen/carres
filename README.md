# Cars reservation

This is a test project. It's created to put together all the knowledge I have about creating web apps with Spring (and eventually also Javascript and some DevOps practiques).

## Applications and parts

- [Spring Boot API](apps/api/README.md)
- [React (Next.js) frontend app](apps/website/README.md)

## Ideas

This whole project is expected to visualize the idea of the web application. The API is covered with tests thoroughly, both in unit and integration style. The domain area of the app is rather simple, so it takes no Ubiquitous Language to describe. Also, DDD techniques were intentionally not used to speed up the development and also out of lack of reason to use ones: the project is small, as I already said.

The project is hosted at Digital Ocean, using Kubernetes setup.
The Continuous Integration is done using GitHub actions.
The secrets are managed using Hashicorp Vault.

## Remarkable elements (you should better take a look at)

- [**Public Sonarcloud report**](https://sonarcloud.io/project/overview?id=leathermen_carres_api)
- [TODO public Grafana dashboard of business metrics](somewhere)

## Credits

The author: Nikita Pavlovskiy, [nikitades@pm.me](nikitades@pm.me).

Contact me in Telegram: [@nikitades](https://t.me/nikitades).