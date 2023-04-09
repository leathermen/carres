package com.nikitades.carres.application.exception;

import lombok.Getter;

@Getter
public abstract class AnnotatedException extends RuntimeException {

  private final String message;
  private final String code;

  public AnnotatedException(String message, String code) {
    super(message);
    this.message = message;
    this.code = code;
  }
}
