package org.kirsch.service.pathfinding;

import com.google.maps.places.v1.Place;
import com.google.type.LatLng;
import java.util.List;

public interface IPathFinder {

  List<Place> buildRoute(LatLng origin, LatLng destination);

}