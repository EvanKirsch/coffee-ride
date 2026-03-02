package io.coffeeride.model;

import com.google.maps.routing.v2.Route;
import io.coffeeride.adaptors.PlaceAdaptor;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class RouteDetails {

  private final List<Route> routeList;
  private final List<PlaceAdaptor> intermediates;

}