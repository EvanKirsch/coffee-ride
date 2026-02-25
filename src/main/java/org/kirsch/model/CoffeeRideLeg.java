package org.kirsch.model;

import lombok.Data;

@Data
public final class CoffeeRideLeg {

  private final CoffeeRidePlace origin;
  private final CoffeeRidePlace destination;
  private final String encodedPolyline;

}