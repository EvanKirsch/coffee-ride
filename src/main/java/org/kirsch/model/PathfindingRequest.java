package org.kirsch.model;

import org.kirsch.model.gcs.Length;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class PathfindingRequest {

  private final String orgAddress;
  private final String dstAddress;
  private final Length step;

}