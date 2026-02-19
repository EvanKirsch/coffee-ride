package org.kirsch.service.pathfinding;

import org.kirsch.model.RouteDetails;
import org.kirsch.model.PathfindingRequest;

public interface IPathFinder {

  RouteDetails buildRoute(PathfindingRequest pathfindingRequest);

}