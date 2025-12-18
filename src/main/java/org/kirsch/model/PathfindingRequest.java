package org.kirsch.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PathfindingRequest {

  private double orgLat;
  private double orgLng;
  private double dstLat;
  private double dstLng;
  private double step;

}