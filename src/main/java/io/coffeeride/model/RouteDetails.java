package io.coffeeride.model;

import io.coffeeride.adaptors.PlaceAdaptor;
import io.coffeeride.adaptors.RouteAdaptor;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class RouteDetails {

  private final List<RouteAdaptor> routeList;
  private final List<PlaceAdaptor> intermediates;

}