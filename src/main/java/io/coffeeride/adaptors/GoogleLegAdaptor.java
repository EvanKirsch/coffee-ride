package io.coffeeride.adaptors;

import com.google.maps.routing.v2.RouteLeg;
import io.coffeeride.model.gcs.Coordinate;
import io.coffeeride.model.gcs.LatLng;
import java.util.List;

public final class GoogleLegAdaptor implements LegAdaptor {

  private final RouteLeg leg;

  public GoogleLegAdaptor(RouteLeg routeLeg) {
    this.leg = routeLeg;
  }

  @Override
  public PlaceAdaptor getStartLocation() {
    return new GooglePlaceAdaptor(leg.getStartLocation().getLatLng());
  }

  @Override
  public String getEncodedPolyline() {
    return leg.getPolyline().getEncodedPolyline();
  }

  public List<LatLng> getStepsList() {
    return leg.getStepsList().stream().map(elt ->
        new LatLng(
            Coordinate.fromDegrees(elt.getStartLocation().getLatLng().getLatitude()),
            Coordinate.fromDegrees(elt.getStartLocation().getLatLng().getLongitude())
        )
    ).toList();
  }

}