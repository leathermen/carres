package com.nikitades.carres.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//I wish we had something like a model builder with a fluent interface.
//But since it's not possible in Java and Hibernate, I prefer this way, even though it brings the data persistence data
//into te domain, which generally is not desired.
//A compromise.
@Entity
@Table(name = "cars")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Car {

  @Id
  private UUID id;

  @Column
  private String manufacturer;

  @Column
  private String model;

  @Column
  private Instant manufacturedAt;

  @Column
  private Instant createdAt;

  @Column
  private Instant updatedAt;

  @Column
  private UUID createdBy;

  @Column
  private boolean available = true;
}
