package io.coffeeride.controller;

import java.util.List;

import io.coffeeride.model.PathfindingRequest;
import io.coffeeride.model.PathfindingRequestStr;
import io.coffeeride.model.PathfindingResponse;
import io.coffeeride.model.RouteDetails;
import io.coffeeride.service.pathfinding.IPathFinder;
import io.coffeeride.service.pathfinding.SdtPathFinder;
import io.coffeeride.validation.IValidationService;
import io.coffeeride.validation.ValidationService;
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
  public PathfindingResponse findRoute(@RequestBody PathfindingRequestStr requestStr)
      throws Exception {
    List<Exception> errors = validationService.validate(requestStr, PathfindingRequestStr.class);
    PathfindingResponse response;
    if (errors.isEmpty()) {
      PathfindingRequest request = conversionService.convert(requestStr, PathfindingRequest.class);
      RouteDetails route = pathFinder.buildRoute(request);
      response = conversionService.convert(route, PathfindingResponse.class);

    } else {
      throw errors.get(0);

    }

    return response;
  }

}