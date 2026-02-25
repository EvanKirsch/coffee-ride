package org.kirsch.util.distance;

import org.kirsch.model.gcs.Coordinate;
import org.kirsch.model.gcs.LatLng;
import org.springframework.stereotype.Component;

@Component
final class FlatDistanceCalculator extends DistanceCalculator {

  static final double METERS_PER_DEGREE = 111111.1;

  public FlatDistanceCalculator build() {
    return new FlatDistanceCalculator();
  }

  public double approxDistance(LatLng p0, LatLng p1) {
    double latDif = distance(p0.getLatitude(), p1.getLatitude());
    double lngDif = distance(p0.getLongitude(), p1.getLongitude());
    double latLngDist = Math.sqrt(latDif + lngDif);

    return (latLngDist * METERS_PER_DEGREE);
  }

  double relativeGapDist(LatLng p0, LatLng p1, double stepMeters) {
    return stepMeters;
  }

  public LatLng findNextTarget(LatLng p0, LatLng p1, double flatGap) {
    double vLat = p0.getLatitude().toDegrees() - p1.getLatitude().toDegrees();
    double vLng = p0.getLongitude().toDegrees() - p1.getLongitude().toDegrees();
    double latLngDist = approxDistance(p0, p1);
    double gapDist = relativeGapDist(p0, p1, flatGap);

    if (gapDist >= latLngDist) {
      return p1;
    } else {
      double uvLat = ((vLat / latLngDist) * gapDist);
      double uvLng = ((vLng / latLngDist) * gapDist);
      return LatLng.builder()
          .latitude(Coordinate.fromDegrees(p0.getLatitude().toDegrees() - uvLat))
          .longitude(Coordinate.fromDegrees(p0.getLongitude().toDegrees() - uvLng))
          .build();
    }
  }

}