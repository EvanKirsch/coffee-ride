package org.kirsch.converter;

import com.google.maps.routing.v2.Location;
import com.google.maps.routing.v2.Waypoint;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.kirsch.model.gcs.LatLng;

@Component
public final class WaypointGConverter implements Converter<LatLng, Waypoint> {

  @Override
  public Waypoint convert(@NonNull LatLng latLng) {
    com.google.type.LatLng ll = com.google.type.LatLng.newBuilder()
        .setLongitude(latLng.getLongitude().toDegrees())
        .setLatitude(latLng.getLatitude().toDegrees())
        .build();

    return Waypoint.newBuilder()
        .setLocation(Location.newBuilder()
            .setLatLng(ll)
            .build())
        .build();
  }

}