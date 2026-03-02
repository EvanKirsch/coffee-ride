package io.coffeeride.adaptors;

import com.google.maps.routing.v2.Location;

public interface LegAdaptor {

  // todo - remove location
  Location getStartLocation();

  String getEncodedPolyline();

}