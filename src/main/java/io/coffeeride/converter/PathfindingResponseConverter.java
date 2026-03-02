package io.coffeeride.converter;

import io.coffeeride.adaptors.LegAdaptor;
import io.coffeeride.adaptors.PlaceAdaptor;
import io.coffeeride.adaptors.RouteAdaptor;
import io.coffeeride.model.CoffeeRideLeg;
import io.coffeeride.model.CoffeeRidePlace;
import io.coffeeride.model.PathfindingResponse;
import io.coffeeride.model.RouteDetails;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public final class PathfindingResponseConverter implements
    Converter<RouteDetails, PathfindingResponse> {

  @Override
  public PathfindingResponse convert(RouteDetails routeDetails) {
    RouteAdaptor route = routeDetails.getRouteList().get(0);
    String encodedPolyline = route.getEncodedPolyline();

    List<CoffeeRideLeg> legs = new ArrayList<>();
    for (int i = 0; i < route.getLegsList().size() && i < routeDetails.getIntermediates().size();
        i++) {
      LegAdaptor leg = route.getLegsList().get(i);
      PlaceAdaptor place = routeDetails.getIntermediates().get(i);
      legs.add(convertLeg(leg, place));
    }

    return new PathfindingResponse(legs, encodedPolyline);
  }

  private CoffeeRideLeg convertLeg(LegAdaptor leg, PlaceAdaptor place) {
    CoffeeRidePlace origin = new CoffeeRidePlace(leg.getStartLocation());
    CoffeeRidePlace destination = new CoffeeRidePlace(place);

    return new CoffeeRideLeg(origin, destination, leg.getEncodedPolyline());
  }

}