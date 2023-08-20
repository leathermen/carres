package com.nikitades.carres.entrypoint.mock.security;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This is the list of "users" for the mock purposes only.
 * Each user contains some settings that affect the way this user is displayed across the call stack of the application.
 * Users are supposed to have various access levels and reliability.
 * Please modify these users according your purposes, but remember that every change will likely affect many tests.
 */
public class Users {

  public static final User Nikita = new User(
    UUID.fromString("34db4e9a-70a8-4127-8292-990acc7f5f24"),
    "Nikita",
    "Pavlovskiy",
    "nikitades@pm.me",
    List.of(JwtRole.Manager),
    "PLEASE_PLEASE_HIRE_ME",
    List.of("openid", "profile", "email")
  );

  public static final User Chuck = new User(
    UUID.fromString("74e2d42e-7e55-4119-8208-947c06cfe987"),
    "Chuck",
    "Norris",
    "chuck.norris@nikitades.com",
    List.of(JwtRole.Manager),
    "VIOLENCE_IS_MY_LAST_OPTION",
    List.of("openid", "profile", "email")
  );

  public static final User Keanu = new User(
    UUID.fromString("b020740b-ea79-447e-acb9-28c539cca909"),
    "Keanu",
    "Reeves",
    "keanu.reeves@nikitades.com",
    List.of(),
    "I_KNOW_KUNG_FU",
    List.of("openid", "profile", "email")
  );

  /**
   * It's important to keep this as the unprivileged/underprivileged one
   */
  public static final User Thomas = new User(
    UUID.fromString("903df77b-f156-4fcc-bf2e-907b20b05e36"),
    "Thomas",
    "Felton",
    "thomas.felton@nikitades.com",
    List.of(),
    "ILL_TELL_MY_FATHER",
    List.of("openid", "profile", "email")
  );

  public static final Map<MockJwt, User> registry = Map.of(
    MockJwt.Nikita,
    Nikita,
    MockJwt.Chuck,
    Chuck,
    MockJwt.Keanu,
    Keanu,
    MockJwt.Thomas,
    Thomas
  );
}
