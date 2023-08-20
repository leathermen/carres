# Cars Reservation System - UI

This is a [Next.js](https://nextjs.org/) project bootstrapped with [`create-next-app`](https://github.com/vercel/next.js/tree/canary/packages/create-next-app).

This application is not the first-class project here and was created rather quickly. The main goal was to produce an app that's capable of displaying the reactive UI, server side rendering and handling the SSO authentication mechanism. This app authenticates against the remote Keycloak server and hides the actual application tokens from the client side. So, technically it's the non-public client (in Keycloak / SSO / OAuth2 terms).

Once the token is expired, the refresh request is made under the hood without the need to reauthenticate.