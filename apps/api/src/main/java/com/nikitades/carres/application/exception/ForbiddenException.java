package com.nikitades.carres.application.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ForbiddenException extends RuntimeException {

  public ForbiddenException(String message) {
    super(message);
  }
}
