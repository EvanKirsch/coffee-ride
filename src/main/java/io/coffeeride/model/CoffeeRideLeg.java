package io.coffeeride.model;

import io.coffeeride.model.gcs.LatLng;
import java.util.List;
import lombok.Data;

@Data
public final class CoffeeRideLeg {

  private final CoffeeRidePlace origin;
  private final CoffeeRidePlace destination;
  private final List<LatLng> stepsList;
  private final String encodedPolyline;

}