package io.coffeeride.converter;

import lombok.NonNull;
import io.coffeeride.model.PathfindingRequest;
import io.coffeeride.model.PathfindingRequestStr;
import io.coffeeride.model.gcs.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public final class PathfindingRequestConverter implements Converter<PathfindingRequestStr, PathfindingRequest> {

  private static final Logger log = LoggerFactory.getLogger(PathfindingRequestConverter.class);

  @Override
  public PathfindingRequest convert(@NonNull PathfindingRequestStr source) {
    Length step = Length.fromMiles(convertStringToDouble(source.getStepMiles()));

    return PathfindingRequest.builder()
        .orgAddress(source.getOrigin())
        .dstAddress(source.getDestination())
        .step(step)
        .build();
  }

  private double convertStringToDouble(String s) {
    if (s.isEmpty()) {
      return 0.0;
    }

    double d;
    try {
      d = Double.parseDouble(s);
    } catch (NumberFormatException e) {
      log.warn(e.getMessage());
      d = 0;
    }
    return d;
  }

}