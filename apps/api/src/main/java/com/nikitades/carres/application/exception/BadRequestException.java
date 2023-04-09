package com.nikitades.carres.application.exception;

public class BadRequestException extends AnnotatedException {

  public BadRequestException(String message, String code) {
    super(message, code);
  }
}
