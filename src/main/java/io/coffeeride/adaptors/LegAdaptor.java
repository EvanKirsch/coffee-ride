package io.coffeeride.adaptors;

import io.coffeeride.model.gcs.LatLng;
import java.util.List;

public interface LegAdaptor {

  // todo - remove location
  PlaceAdaptor getStartLocation();

  String getEncodedPolyline();

  List<LatLng> getStepsList();

}