package com.nikitades.carres.shared;

import java.io.Serializable;

public record NewReservationNotification(String email, String manufacturer, String model)
  implements Serializable {}
