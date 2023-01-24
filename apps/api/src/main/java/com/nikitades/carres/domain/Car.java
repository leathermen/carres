package com.nikitades.carres.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity
public class Car {

  @Id
  private UUID id;

  @Column
  private String manufacturer;

  @Column
  private String model;

  @Column
  private Date manufacturedAt;

  @Column
  private Date createdAt;

  @Column
  private Date updatedAt;

  @Column
  private UUID createdBy;

  @Column
  private boolean available = true;
}
