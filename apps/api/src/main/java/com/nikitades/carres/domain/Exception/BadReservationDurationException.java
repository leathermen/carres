package com.nikitades.carres.domain.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BadReservationDurationException extends Exception {

  private final String message;
}
