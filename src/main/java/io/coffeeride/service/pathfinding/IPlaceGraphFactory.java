package io.coffeeride.service.pathfinding;

import com.google.maps.places.v1.Place;
import java.util.List;
import io.coffeeride.model.WeightedPlaceGraph;
import io.coffeeride.model.gcs.LatLng;

public interface IPlaceGraphFactory {

  WeightedPlaceGraph createGraph(List<Place> places, LatLng origin, LatLng target);

}