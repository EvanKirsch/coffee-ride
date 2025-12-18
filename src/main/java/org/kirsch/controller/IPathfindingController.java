package org.kirsch.controller;

import org.kirsch.model.PathfindingRequestStr;
import org.kirsch.model.PathfindingResponse;

public interface IPathfindingController {

  PathfindingResponse findRoute(PathfindingRequestStr pathfindingRequest);

}