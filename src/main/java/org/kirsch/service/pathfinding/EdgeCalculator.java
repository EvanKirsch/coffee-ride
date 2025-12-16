package org.kirsch.service.pathfinding;

import org.kirsch.model.WeightedPlaceGraph;
import org.kirsch.util.DebugUtil;

public class EdgeCalculator {

  private final DebugUtil debugUtil;

  public EdgeCalculator() {
    this.debugUtil = DebugUtil.getInstance();
  }

  public WeightedPlaceGraph sortNodes(WeightedPlaceGraph graph) {
    graph.getNodes().sort((n0, n1) -> {
      return n0.getDistanceToTerminus() < n1.getDistanceToTerminus() ? -1 : 1;
    });

    graph.getNodes()
        .removeIf(elt -> elt.getDistanceToStart() == 0);

    debugUtil.printGraph(graph);
    return graph;
  }

}