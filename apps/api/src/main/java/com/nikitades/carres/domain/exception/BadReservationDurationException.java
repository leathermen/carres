package com.nikitades.carres.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BadReservationDurationException extends Exception {

  private final String message;
}
