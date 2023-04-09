package com.nikitades.carres.application.exception;

public class ForbiddenException extends AnnotatedException {

  public ForbiddenException(String message, String code) {
    super(message, code);
  }
}
