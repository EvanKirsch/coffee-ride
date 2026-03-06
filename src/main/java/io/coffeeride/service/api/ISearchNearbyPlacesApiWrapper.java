package io.coffeeride.service.api;

import io.coffeeride.adaptors.PlaceAdaptor;
import io.coffeeride.model.gcs.LatLng;
import io.coffeeride.util.exception.CoffeeRideApiException;
import java.util.List;

public interface ISearchNearbyPlacesApiWrapper {

  List<PlaceAdaptor> searchNearby(LatLng origin, LatLng destination) throws CoffeeRideApiException;

}