package org.kirsch.service.pathfinding;

import com.google.maps.places.v1.Place;
import com.google.maps.routing.v2.Route;
import java.util.ArrayList;
import java.util.List;
import org.kirsch.model.RouteDetails;
import org.kirsch.model.Node;
import org.kirsch.model.PathfindingRequest;
import org.kirsch.model.WeightedPlaceGraph;
import org.kirsch.service.api.GeocodeApiWrapper;
import org.kirsch.service.api.IGeocodeApiWrapper;
import org.kirsch.service.api.IRoutesApiWrapper;
import org.kirsch.service.api.ISearchNearbyPlacesApiWrapper;
import org.kirsch.service.api.RoutesApiWrapper;
import org.kirsch.service.api.SearchNearbyPlacesApiWrapper;
import org.kirsch.util.distance.IDistanceCalculator;
import org.kirsch.util.distance.SphereDistanceCalculatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.kirsch.model.gcs.LatLng;

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
  public SdtPathFinder(SearchNearbyPlacesApiWrapper searchPlacesWrapper,
      RoutesApiWrapper routesApiWrapper,
      EdgeCalculator edgeCalculator,
      IPlaceGraphFactory graphFactory,
      GeocodeApiWrapper geocodeApiWrapper,
      SphereDistanceCalculatorFactory dcFactory,
      ConversionService conversionService
      ) {
    this.routesApiWrapper = routesApiWrapper;
    this.searchPlacesWrapper = searchPlacesWrapper;
    this.edgeCalculator = edgeCalculator;
    this.graphFactory = graphFactory;
    this.geocodeApiWrapper = geocodeApiWrapper;
    this.distanceCalculator = dcFactory.getCalculator();
    this.conversionService = conversionService;
  }

  @Override
  public RouteDetails buildRoute(PathfindingRequest pathfindingRequest) {
    LatLng target;
    List<Place> intermediates = new ArrayList<>();

    LatLng origin = geocodeApiWrapper.geocode(pathfindingRequest.getOrgAddress());
    LatLng curOrigin = origin;
    LatLng destination = geocodeApiWrapper.geocode(pathfindingRequest.getDstAddress());
    boolean isDeadEnd = false;
    int i = 0;
    do {
      i++;
      target = distanceCalculator.findNextTarget(curOrigin, destination, pathfindingRequest.getStep());
      List<Place> places = searchPlacesWrapper.searchNearby(curOrigin, target);
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
    List<Route> routes = routesApiWrapper.computeRoute(origin, destination, intermediates);
    return new RouteDetails(routes, intermediates);
  }

}