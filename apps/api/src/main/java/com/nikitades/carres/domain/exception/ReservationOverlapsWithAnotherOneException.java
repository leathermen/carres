package com.nikitades.carres.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReservationOverlapsWithAnotherOneException extends Exception {

  private final String message;
}
