#!/bin/sh

NEXTAUTH_URL
KEYCLOAK_CLIENT_ID
KEYCLOAK_CLIENT_SECRET
KEYCLOAK_WELL_KNOWN_ADDRESS

echo "Replacing <NEXTAUTH_SECRET>..."
test -n "$NEXTAUTH_SECRET"

find /app/.next \( -type d -name .git -prune \) -o -type f -print0 | xargs -0 sed -i "s#APP_NEXTAUTH_SECRET#$NEXTAUTH_SECRET#g"

echo "Replacing <NEXTAUTH_URL>..."
test -n "$NEXTAUTH_URL"

find /app/.next \( -type d -name .git -prune \) -o -type f -print0 | xargs -0 sed -i "s#APP_NEXTAUTH_URL#$NEXTAUTH_URL#g"

echo "Replacing <KEYCLOAK_CLIENT_ID>..."
test -n "$KEYCLOAK_CLIENT_ID"

find /app/.next \( -type d -name .git -prune \) -o -type f -print0 | xargs -0 sed -i "s#APP_KEYCLOAK_CLIENT_ID#$KEYCLOAK_CLIENT_ID#g"

echo "Replacing <KEYCLOAK_CLIENT_SECRET>..."
test -n "$KEYCLOAK_CLIENT_SECRET"

find /app/.next \( -type d -name .git -prune \) -o -type f -print0 | xargs -0 sed -i "s#APP_KEYCLOAK_CLIENT_SECRET#$KEYCLOAK_CLIENT_SECRET#g"

echo "Replacing <KEYCLOAK_WELL_KNOWN_ADDRESS>..."
test -n "$KEYCLOAK_WELL_KNOWN_ADDRESS"

find /app/.next \( -type d -name .git -prune \) -o -type f -print0 | xargs -0 sed -i "s#APP_KEYCLOAK_WELL_KNOWN_ADDRESS#$KEYCLOAK_WELL_KNOWN_ADDRESS#g"

echo "Starting CarResWebsite..."
exec "$@"
