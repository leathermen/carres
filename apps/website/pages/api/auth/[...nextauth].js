import NextAuth from "next-auth";
import Keycloak from "next-auth/providers/keycloak";
import refreshAccessToken from "../../../utils/api/refreshAccessToken";

const options = {
  providers: [
    Keycloak({
      clientId: process.env.KEYCLOAK_CLIENT_ID,
      clientSecret: process.env.KEYCLOAK_CLIENT_SECRET,
      issuer: process.env.KEYCLOAK_REALM_ADDRESS,
    }),
  ],
  secret: process.env.NEXTAUTH_SECRET,
  callbacks: {
    async jwt({ token, user, account }) {
      if (account && user) {
        console.log("AUTH!", account);
        return {
          ...token,
          accessToken: account.access_token,
          accessTokenExpires: account.expires_at * 1000,
          refreshToken: account.refresh_token,
          idToken: account.id_token,
        };
      }

      if (Date.now() < token.accessTokenExpires) {
        return token;
      }

      return refreshAccessToken(token);
    },
    async session({ session, token }) {
      session.tokenSet = {
        accessToken: token.accessToken,
        refreshToken: token.refreshToken,
        accessTokenExpires: token.accessTokenExpires,
        idToken: token.idToken,
      };

      return session;
    },
  },
};

export default (req, res) => {
  try {
    return NextAuth(req, res, options);
  } catch (e) {
    console.log({ e });
  }
};
