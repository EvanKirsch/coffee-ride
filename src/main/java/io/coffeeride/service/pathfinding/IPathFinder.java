package io.coffeeride.service.pathfinding;

import io.coffeeride.model.RouteDetails;
import io.coffeeride.model.PathfindingRequest;

public interface IPathFinder {

  RouteDetails buildRoute(PathfindingRequest pathfindingRequest);

}