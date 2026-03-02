package io.coffeeride.model;

import io.coffeeride.adaptors.PlaceAdaptor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class Node {

  private final PlaceAdaptor place;
  private final double distanceToTerminus;
  private final double distanceToStart;

}