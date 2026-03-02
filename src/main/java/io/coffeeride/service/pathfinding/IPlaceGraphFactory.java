package io.coffeeride.service.pathfinding;

import io.coffeeride.adaptors.PlaceAdaptor;
import io.coffeeride.model.WeightedPlaceGraph;
import io.coffeeride.model.gcs.LatLng;
import java.util.List;

public interface IPlaceGraphFactory {

  WeightedPlaceGraph createGraph(List<PlaceAdaptor> places, LatLng origin, LatLng target);

}