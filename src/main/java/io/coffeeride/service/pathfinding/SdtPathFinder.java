package io.coffeeride.service.pathfinding;

import io.coffeeride.adaptors.PlaceAdaptor;
import io.coffeeride.adaptors.RouteAdaptor;
import io.coffeeride.model.Node;
import io.coffeeride.model.PathfindingRequest;
import io.coffeeride.model.RouteDetails;
import io.coffeeride.model.WeightedPlaceGraph;
import io.coffeeride.model.gcs.LatLng;
import io.coffeeride.service.api.IGeocodeApiWrapper;
import io.coffeeride.service.api.IRoutesApiWrapper;
import io.coffeeride.service.api.ISearchNearbyPlacesApiWrapper;
import io.coffeeride.util.distance.IDistanceCalculator;
import io.coffeeride.util.exception.CoffeeRideApiException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class SdtPathFinder implements IPathFinder {

  private final ISearchNearbyPlacesApiWrapper searchPlacesWrapper;
  private final IRoutesApiWrapper routesApiWrapper;
  private final EdgeCalculator edgeCalculator;
  private final IPlaceGraphFactory graphFactory;
  private final IGeocodeApiWrapper geocodeApiWrapper;
  private final IDistanceCalculator distanceCalculator;
  private final ConversionService conversionService;

  @Autowired
  public SdtPathFinder(ISearchNearbyPlacesApiWrapper searchPlacesWrapper,
      IRoutesApiWrapper routesApiWrapper,
      EdgeCalculator edgeCalculator,
      IPlaceGraphFactory graphFactory,
      IGeocodeApiWrapper geocodeApiWrapper,
      IDistanceCalculator distanceCalculator,
      ConversionService conversionService
  ) {
    this.routesApiWrapper = routesApiWrapper;
    this.searchPlacesWrapper = searchPlacesWrapper;
    this.edgeCalculator = edgeCalculator;
    this.graphFactory = graphFactory;
    this.geocodeApiWrapper = geocodeApiWrapper;
    this.distanceCalculator = distanceCalculator;
    this.conversionService = conversionService;
  }

  @Override
  public RouteDetails buildRoute(PathfindingRequest pathfindingRequest)
      throws CoffeeRideApiException {
    LatLng target;
    List<PlaceAdaptor> intermediates = new ArrayList<>();

    LatLng origin = geocodeApiWrapper.geocode(pathfindingRequest.getOrgAddress());
    LatLng curOrigin = origin;
    LatLng destination = geocodeApiWrapper.geocode(pathfindingRequest.getDstAddress());
    boolean isDeadEnd = false;
    int i = 0;
    do {
      i++;
      target = distanceCalculator.findNextTarget(curOrigin, destination,
          pathfindingRequest.getStep());
      List<PlaceAdaptor> places = searchPlacesWrapper.searchNearby(curOrigin, target);
      WeightedPlaceGraph graph = graphFactory.createGraph(places, curOrigin, target);
      edgeCalculator.sortNodes(graph);
      List<Node> nodes = graph.getNodes();
      if (nodes != null && !nodes.isEmpty()) {
        curOrigin = conversionService.convert(nodes.get(0).getPlace().getLocation(), LatLng.class);
        intermediates.add(nodes.get(0).getPlace());
      } else {
        isDeadEnd = true;
      }
    } while (target != destination && !isDeadEnd && i < 20);
    List<RouteAdaptor> routes = routesApiWrapper.computeRoute(origin, destination, intermediates);
    return new RouteDetails(routes, intermediates);
  }

}