package io.coffeeride.service.pathfinding;

import io.coffeeride.model.RouteDetails;
import io.coffeeride.model.PathfindingRequest;
import io.coffeeride.util.exception.CoffeeRideApiException;

public interface IPathFinder {

  RouteDetails buildRoute(PathfindingRequest pathfindingRequest) throws CoffeeRideApiException;

}