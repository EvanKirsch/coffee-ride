package org.kirsch.service.api;

import com.google.maps.places.v1.Place;
import java.util.List;
import org.kirsch.model.gcs.LatLng;

public interface ISearchNearbyPlacesApiWrapper {

  List<Place> searchNearby(LatLng origin, LatLng destination);

}
