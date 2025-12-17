package org.kirsch.service.pathfinding;

import com.google.maps.places.v1.Place;
import java.util.List;
import org.kirsch.model.PathfindingRequest;
import org.kirsch.model.PathfindingResponse;

public interface IPathFinder {

  PathfindingResponse buildRoute(PathfindingRequest pathfindingRequest);

}