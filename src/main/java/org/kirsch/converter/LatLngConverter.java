package org.kirsch.converter;

import org.jetbrains.annotations.NotNull;
import org.kirsch.model.gcs.Coordinate;
import org.kirsch.model.gcs.LatLng;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public final class LatLngConverter implements Converter<com.google.type.LatLng, LatLng> {

  @Override
  public LatLng convert(@NotNull com.google.type.LatLng ll) {
    return LatLng.builder()
        .latitude(Coordinate.fromDegrees(ll.getLatitude()))
        .longitude(Coordinate.fromDegrees(ll.getLongitude()))
        .build();
  }

}