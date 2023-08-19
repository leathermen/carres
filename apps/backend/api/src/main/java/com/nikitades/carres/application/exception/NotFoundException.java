package com.nikitades.carres.application.exception;

public class NotFoundException extends AnnotatedException {

  public NotFoundException(String message, String code) {
    super(message, code);
  }
}
