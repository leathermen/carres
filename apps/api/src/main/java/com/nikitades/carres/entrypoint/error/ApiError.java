package com.nikitades.carres.entrypoint.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiError {

  private String message;
  private String code;
}
