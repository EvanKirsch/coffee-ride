package io.coffeeride.adaptors;

import io.coffeeride.model.gcs.LatLng;

public interface PlaceAdaptor {

  String getDisplayName();
  String getAddress();
  double getLatitude();
  double getLongitude();
  String getName();
  LatLng getLocation();

}