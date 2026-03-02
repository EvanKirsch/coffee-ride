package io.coffeeride.service.api;

import com.google.maps.places.v1.Place;
import java.util.List;
import io.coffeeride.model.gcs.LatLng;

public interface ISearchNearbyPlacesApiWrapper {

  List<Place> searchNearby(LatLng origin, LatLng destination);

}
