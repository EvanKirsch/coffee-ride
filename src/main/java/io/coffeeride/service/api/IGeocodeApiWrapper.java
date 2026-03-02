package io.coffeeride.service.api;

import io.coffeeride.model.gcs.LatLng;

public interface IGeocodeApiWrapper {

  LatLng geocode(String address);

}