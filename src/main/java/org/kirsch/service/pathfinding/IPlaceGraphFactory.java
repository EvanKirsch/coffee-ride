package org.kirsch.service.pathfinding;

import com.google.maps.places.v1.Place;
import java.util.List;
import org.kirsch.model.WeightedPlaceGraph;
import org.kirsch.model.gcs.LatLng;

public interface IPlaceGraphFactory {

  WeightedPlaceGraph createGraph(List<Place> places, LatLng origin, LatLng target);

}