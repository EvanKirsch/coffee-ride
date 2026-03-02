package io.coffeeride.util;

import com.google.maps.places.v1.Place;
import java.util.List;
import io.coffeeride.model.Node;
import io.coffeeride.model.WeightedPlaceGraph;

public final class DebugUtil {

  private static DebugUtil debugUtil;

  private DebugUtil() {

  }

  public static DebugUtil getInstance() {
    if (debugUtil == null) {
      debugUtil = new DebugUtil();
    }
    return debugUtil;
  }

  public void printPlaces(List<Place> places) {
    for (Place place : places) {
      System.out.println( place.getDisplayName().getText() + ", " + place.getFormattedAddress());
    }
  }

  public void printGraph(WeightedPlaceGraph graph) {
    for (Node node : graph.getNodes()) {
      System.out.println(node.getDistanceToTerminus() + ", " + node.getDistanceToStart()
          + ", " + node.getPlace().getDisplayName().getText()
          + ", " + node.getPlace().getFormattedAddress());
    }
  }

}