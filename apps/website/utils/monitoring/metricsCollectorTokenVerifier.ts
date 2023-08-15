import { JwtHeader, JwtPayload, decode, verify } from "jsonwebtoken";
import { JwksClient, SigningKey } from "jwks-rsa";

const getKey = async (header: JwtHeader): Promise<SigningKey> => {
  const client = new JwksClient({
    jwksUri: `${process.env.KEYCLOAK_REALM_ADDRESS}/protocol/openid-connect/certs`,
  });

  return await client.getSigningKey(header.kid);
};

export const verifyToken = async (token: string, requiredRole: string): Promise<boolean> => {
  try {
    const jwtDecoded = decode(token, { complete: true });
    const signingKey = await getKey(jwtDecoded?.header as JwtHeader);
    const aaa = verify(token, signingKey.getPublicKey(), { complete: true });
    const roles = (aaa.payload as JwtPayload & { roles: string[] })["roles"];
    if (!roles.includes(requiredRole)) {
      throw new Error(`Role <${requiredRole}> is missing from the roles list`);
    }
    return true;
  } catch (err) {
    console.error({ err });
    return false;
  }
};
