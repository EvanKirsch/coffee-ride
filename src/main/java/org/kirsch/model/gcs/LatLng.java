package org.kirsch.model.gcs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public final class LatLng {

  private final Coordinate latitude;
  private final Coordinate longitude;

  @Override
  public boolean equals(Object o) {
    return o instanceof LatLng
        && ((LatLng) o).latitude.equals(this.latitude)
        && ((LatLng) o).longitude.equals(this.longitude);
  }

  @Override
  public String toString() {
    return "{" + this.latitude.toString() + ", " + this.longitude.toString() + "}";
  }

}