package io.coffeeride.adaptors;

import com.google.maps.routing.v2.Route;
import java.util.List;
import java.util.stream.Collectors;

public final class GoogleRouteAdaptor implements RouteAdaptor {

  private final Route route;

  public GoogleRouteAdaptor(Route route) {
    this.route = route;
  }

  @Override
  public List<LegAdaptor> getLegsList() {
    return route.getLegsList().stream()
        .map(GoogleLegAdaptor::new)
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  public String getEncodedPolyline() {
    return route.getPolyline().getEncodedPolyline();
  }

}