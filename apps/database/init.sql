CREATE USER carres;
ALTER USER carres WITH PASSWORD 'carres';
CREATE DATABASE carres;
GRANT ALL ON DATABASE carres TO carres;
ALTER DATABASE carres OWNER TO carres;

CREATE USER keycloak;
ALTER USER keycloak WITH PASSWORD 'keycloak';
CREATE DATABASE keycloak;
GRANT ALL ON DATABASE keycloak TO keycloak;
ALTER DATABASE keycloak OWNER TO keycloak;