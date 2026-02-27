package org.kirsch.controller;

import java.util.List;

import org.kirsch.model.PathfindingRequest;
import org.kirsch.model.PathfindingRequestStr;
import org.kirsch.model.PathfindingResponse;
import org.kirsch.model.RouteDetails;
import org.kirsch.service.pathfinding.IPathFinder;
import org.kirsch.service.pathfinding.SdtPathFinder;
import org.kirsch.validation.IValidationService;
import org.kirsch.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pathfinding")
public class PathfindingController implements IPathfindingController {

  private final IPathFinder pathFinder;
  private final ConversionService conversionService;
  private final IValidationService validationService;

  @Autowired
  public PathfindingController(SdtPathFinder pathFinder, ConversionService conversionService, ValidationService validationService) {
    this.pathFinder = pathFinder;
    this.conversionService = conversionService;
    this.validationService = validationService;
  }

  @PutMapping
  @ResponseBody
  @Override
  public PathfindingResponse findRoute(@RequestBody PathfindingRequestStr requestStr) {
    List<Exception> errors = validationService.validate(requestStr, PathfindingRequestStr.class);
    PathfindingResponse response;
    if (errors.isEmpty()) {
      PathfindingRequest request = conversionService.convert(requestStr, PathfindingRequest.class);
      RouteDetails route = pathFinder.buildRoute(request);
      response = conversionService.convert(route, PathfindingResponse.class);

    } else {
      response = null; // TODO - handle errors

    }
    return response;
  }

}