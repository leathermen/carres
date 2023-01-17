import axios from "axios";

/**
 * Takes a token, and returns a new token with updated
 * `accessToken` and `accessTokenExpires`. If an error occurs,
 * returns the old token and an error property
 */
export default async function refreshAccessToken(token) {
  try {
    const configRes = await axios.get(
      `${process.env.KEYCLOAK_REALM_ADDRESS}/.well-known/openid-configuration`
    );

    const refreshRes = await axios.post(
      configRes.data.token_endpoint,
      {
        client_id: process.env.KEYCLOAK_CLIENT_ID,
        client_secret: process.env.KEYCLOAK_CLIENT_SECRET,
        grant_type: "refresh_token",
        refresh_token: token.refreshToken,
      },
      {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      }
    );

    return {
      ...token,
      accessToken: refreshRes.data.access_token,
      accessTokenExpires:
        +new Date() + refreshRes.data.refresh_expires_in * 1000,
      refreshToken: refreshRes.data.refresh_token ?? token.refreshToken,
    };
  } catch (error) {
    console.error(error);

    return {
      ...token,
      error: "RefreshAccessTokenError",
    };
  }
}
