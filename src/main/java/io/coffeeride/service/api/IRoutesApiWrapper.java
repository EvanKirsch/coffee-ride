package io.coffeeride.service.api;

import io.coffeeride.adaptors.PlaceAdaptor;
import io.coffeeride.adaptors.RouteAdaptor;
import io.coffeeride.model.gcs.LatLng;
import java.util.List;

public interface IRoutesApiWrapper {

  List<RouteAdaptor> computeRoute(LatLng origin, LatLng destination,
      List<PlaceAdaptor> intermediates);

}