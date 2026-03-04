package io.coffeeride.controller.pathfinding;

import io.coffeeride.model.PathfindingRequestStr;
import io.coffeeride.model.PathfindingResponse;

public interface IPathfindingController {

  PathfindingResponse findRoute(PathfindingRequestStr pathfindingRequest) throws Exception;

}