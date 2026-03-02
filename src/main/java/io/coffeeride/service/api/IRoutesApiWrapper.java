package io.coffeeride.service.api;

import com.google.maps.routing.v2.Route;
import io.coffeeride.adaptors.PlaceAdaptor;
import io.coffeeride.model.gcs.LatLng;
import java.util.List;

public interface IRoutesApiWrapper {

  List<Route> computeRoute(LatLng origin, LatLng destination, List<PlaceAdaptor> intermediates);

}
