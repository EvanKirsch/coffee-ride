package org.kirsch.converter;

import lombok.NonNull;
import org.kirsch.model.PathfindingRequest;
import org.kirsch.model.PathfindingRequestStr;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public final class PathfindingRequestConverter implements Converter<PathfindingRequestStr, PathfindingRequest> {

  @Override
  public PathfindingRequest convert(@NonNull PathfindingRequestStr source) {
    Double orgLat = null;
    Double orgLng = null;
    Double dstLat = null;
    Double dstLng = null;
    double step = 0;

    String origin = source.getOrigin();
    if (origin != null) {
      String[] arr = origin.split(",");
      if (arr.length > 1) {
        orgLat = convertStringToDouble(arr[0]);
        orgLng = convertStringToDouble(arr[1]);
      }
    }
    String destination = source.getDestination();
    if (destination != null) {
      String[] arr = destination.split(",");
      if (arr.length > 1) {
        dstLat = convertStringToDouble(arr[0]);
        dstLng = convertStringToDouble(arr[1]);
      }
    }

    step = convertStringToDouble(source.getStep());

    return PathfindingRequest.builder()
        .orgLat(orgLat == null ? 0.0 : orgLat)
        .orgLng(orgLng == null ? 0.0 : orgLng)
        .dstLat(dstLat == null ? 0.0 : dstLat)
        .dstLng(dstLng == null ? 0.0 : dstLng)
        .step(step)
        .build();
  }

  private Double convertStringToDouble(String s) {
    if (s.isEmpty()) {
      return 0.0;
    }

    double d;
    try {
      d = Double.parseDouble(s);
    } catch (NumberFormatException e) {
      e.printStackTrace();
      d = 0;
    }
    return d;
  }

}