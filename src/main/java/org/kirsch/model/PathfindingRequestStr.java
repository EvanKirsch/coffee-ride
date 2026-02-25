package org.kirsch.model;

import lombok.Data;

@Data
public final class PathfindingRequestStr {

  private final String origin;
  private final String destination;
  private final String stepMiles;

}