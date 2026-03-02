package io.coffeeride.service.api;

import io.coffeeride.adaptors.PlaceAdaptor;
import java.util.List;
import io.coffeeride.model.gcs.LatLng;

public interface ISearchNearbyPlacesApiWrapper {

  List<PlaceAdaptor> searchNearby(LatLng origin, LatLng destination);

}
