package com.nikitades.carres.domain;

import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Car {

  private UUID id;
  private String manufacturer;
  private String model;
  private Date manufacturedAt;
  private Date createdAt;
  private Date updatedAt;
  private UUID createdBy;
  private boolean available = true;
}
