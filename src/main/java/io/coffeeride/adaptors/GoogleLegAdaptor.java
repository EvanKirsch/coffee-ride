package io.coffeeride.adaptors;

import com.google.maps.routing.v2.Location;
import com.google.maps.routing.v2.RouteLeg;

public final class GoogleLegAdaptor implements LegAdaptor {

  private final RouteLeg leg;

  public GoogleLegAdaptor(RouteLeg routeLeg) {
    this.leg = routeLeg;
  }

  @Override
  public Location getStartLocation() {
    return leg.getStartLocation();
  }

  @Override
  public String getEncodedPolyline() {
    return leg.getPolyline().getEncodedPolyline();
  }

}