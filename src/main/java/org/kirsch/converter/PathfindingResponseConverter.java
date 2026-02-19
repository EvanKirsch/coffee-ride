package org.kirsch.converter;

import com.google.maps.places.v1.Place;
import com.google.maps.routing.v2.Route;
import com.google.maps.routing.v2.RouteLeg;
import java.util.ArrayList;
import java.util.List;
import org.kirsch.model.CoffeeRideLeg;
import org.kirsch.model.CoffeeRidePlace;
import org.kirsch.model.PathfindingResponse;
import org.kirsch.model.RouteDetails;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public final class PathfindingResponseConverter implements Converter<RouteDetails, PathfindingResponse> {

  @Override
  public PathfindingResponse convert(RouteDetails routeDetails) {
    Route route = routeDetails.getRouteList().get(0);
    String encodedPolyline = route.getPolyline().getEncodedPolyline();

    List<CoffeeRideLeg> legs = new ArrayList<>();
    for (int i = 0; i < route.getLegsList().size() && i < routeDetails.getIntermediates().size(); i++) {
      RouteLeg leg = route.getLegsList().get(i);
      Place place = routeDetails.getIntermediates().get(i);
      legs.add(convertLeg(leg, place));
    }

    return new PathfindingResponse(legs, encodedPolyline);
  }

  private CoffeeRideLeg convertLeg(RouteLeg leg, Place place) {
    CoffeeRidePlace origin = new CoffeeRidePlace(leg.getStartLocation().getLatLng());
    CoffeeRidePlace destination = new CoffeeRidePlace(place);

    return new CoffeeRideLeg(origin, destination, leg.getPolyline().getEncodedPolyline());
  }

}