package io.coffeeride.service.api;

import io.coffeeride.adaptors.PlaceAdaptor;
import io.coffeeride.model.gcs.LatLng;
import java.util.List;

public interface ISearchNearbyPlacesApiWrapper {

  List<PlaceAdaptor> searchNearby(LatLng origin, LatLng destination);

}