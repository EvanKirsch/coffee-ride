package org.kirsch.service.pathfinding;

import com.google.maps.places.v1.Place;
import java.util.ArrayList;
import java.util.List;
import org.kirsch.model.Node;
import org.kirsch.model.WeightedPlaceGraph;
import org.kirsch.model.gcs.LatLng;
import org.kirsch.util.distance.IDistanceCalculator;
import org.kirsch.util.distance.SphereDistanceCalculatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class WeightedPlaceGraphFactory implements IPlaceGraphFactory {

  private final IDistanceCalculator distanceCalculator;
  private final ConversionService conversionService;

  @Autowired
  public WeightedPlaceGraphFactory(SphereDistanceCalculatorFactory dcFactory,
      ConversionService conversionService) {
    this.distanceCalculator = dcFactory.getCalculator();
    this.conversionService = conversionService;
  }

  public WeightedPlaceGraph createGraph(List<Place> places, LatLng origin, LatLng target) {
    List<Node> nodes = new ArrayList<>();
    for (Place place : places) {
      LatLng latLng = conversionService.convert(place.getLocation(), LatLng.class);
      double distanceToTarget = distanceCalculator.approxDistance(latLng, target).toMeters();
      double distanceToStart = distanceCalculator.approxDistance(latLng, origin).toMeters();
      nodes.add(new Node(place, distanceToTarget, distanceToStart));
    }
    return new WeightedPlaceGraph(nodes);
  }

}