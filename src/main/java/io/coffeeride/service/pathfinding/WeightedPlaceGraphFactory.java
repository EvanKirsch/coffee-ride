package io.coffeeride.service.pathfinding;

import io.coffeeride.adaptors.PlaceAdaptor;
import io.coffeeride.model.Node;
import io.coffeeride.model.WeightedPlaceGraph;
import io.coffeeride.model.gcs.LatLng;
import io.coffeeride.util.distance.IDistanceCalculator;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class WeightedPlaceGraphFactory implements IPlaceGraphFactory {

  private final IDistanceCalculator distanceCalculator;
  private final ConversionService conversionService;

  @Autowired
  public WeightedPlaceGraphFactory(IDistanceCalculator distanceCalculator,
      ConversionService conversionService) {
    this.distanceCalculator = distanceCalculator;
    this.conversionService = conversionService;
  }

  public WeightedPlaceGraph createGraph(List<PlaceAdaptor> places, LatLng origin, LatLng target) {
    List<Node> nodes = new ArrayList<>();
   double maxDistance = distanceCalculator.approxDistance(origin, target).toMeters();
    for (PlaceAdaptor place : places) {
      LatLng latLng = conversionService.convert(place.getLocation(), LatLng.class);
      double distanceToTarget = distanceCalculator.approxDistance(latLng, target).toMeters();
      double distanceToStart = distanceCalculator.approxDistance(latLng, origin).toMeters();
      if (distanceToStart != 0 
        && distanceToStart < maxDistance 
        && distanceToTarget < maxDistance) {
        nodes.add(new Node(place, distanceToTarget, distanceToStart));
      }
    }
    return new WeightedPlaceGraph(nodes);
  }

}