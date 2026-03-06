package io.coffeeride.service.api;

import io.coffeeride.model.gcs.LatLng;
import io.coffeeride.util.exception.CoffeeRideApiException;

public interface IGeocodeApiWrapper {

  LatLng geocode(String address) throws CoffeeRideApiException;

}