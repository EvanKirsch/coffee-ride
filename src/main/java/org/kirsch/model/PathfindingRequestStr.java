package org.kirsch.model;

import lombok.Data;

@Data
public class PathfindingRequestStr {

  private String origin;
  private String destination;
  private String step;

}