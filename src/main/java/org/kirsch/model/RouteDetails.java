package org.kirsch.model;

import com.google.maps.places.v1.Place;
import com.google.maps.routing.v2.Route;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class RouteDetails {

  private final List<Route> routeList;
  private final List<Place> intermediates;

}