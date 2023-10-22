const jsonwebtoken = require("jsonwebtoken");
const jwksrsa = require("jwks-rsa");

/**
 *
 * @param {jsonwebtoken.JwtHeader} header
 * @returns {Promise<jwksrsa.SigningKey>}
 */
const getKey = async (header) => {
  const client = new jwksrsa.JwksClient({
    jwksUri: `${process.env.KEYCLOAK_REALM_ADDRESS}/protocol/openid-connect/certs`,
  });

  return await client.getSigningKey(header.kid);
};

/**
 *
 * @param {string} token
 * @param {string} requiredRole
 * @returns {Promise<boolean>}
 */
const verifyToken = async (token, requiredRole) => {
  try {
    const jwtDecoded = jsonwebtoken.decode(token, { complete: true });
    /** @type {jsonwebtoken.JwtHeader} signingkey */
    const signingKey = await getKey(jwtDecoded?.header);
    /** @type {{payload: jsonwebtoken.JwtPayload & { roles: string[] }}} aaa */
    const aaa = jsonwebtoken.verify(token, signingKey.getPublicKey(), { complete: true });
    const roles = aaa.payload["roles"];
    if (!roles.includes(requiredRole)) {
      throw new Error(`Role <${requiredRole}> is missing from the roles list`);
    }
    return true;
  } catch (err) {
    console.error({ err });
    return false;
  }
};

module.exports = { verifyToken };
