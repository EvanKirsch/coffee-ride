package io.coffeeride.util;

import io.coffeeride.adaptors.PlaceAdaptor;
import io.coffeeride.model.Node;
import io.coffeeride.model.WeightedPlaceGraph;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DebugUtil {

  private static DebugUtil debugUtil;

  private static final Logger log = LoggerFactory.getLogger(DebugUtil.class);

  private DebugUtil() {

  }

  public static DebugUtil getInstance() {
    if (debugUtil == null) {
      debugUtil = new DebugUtil();
    }
    return debugUtil;
  }

  public void printPlaces(List<PlaceAdaptor> places) {
    for (PlaceAdaptor place : places) {
      log.info("{}, {}",
          place.getDisplayName(),
          place.getAddress()
      );
    }
  }

  public void printGraph(WeightedPlaceGraph graph) {
    for (Node node : graph.getNodes()) {
      log.info("{}, {}, {}, {}",
          node.getDistanceToTerminus(),
          node.getDistanceToStart(),
          node.getPlace().getDisplayName(),
          node.getPlace().getAddress()
      );
    }
  }

}