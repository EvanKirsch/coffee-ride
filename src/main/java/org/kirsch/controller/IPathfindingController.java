package org.kirsch.controller;

import org.kirsch.model.PathfindingRequest;
import org.kirsch.model.PathfindingResponse;

public interface IPathfindingController {

  PathfindingResponse findRoute(PathfindingRequest pathfindingRequest);

}