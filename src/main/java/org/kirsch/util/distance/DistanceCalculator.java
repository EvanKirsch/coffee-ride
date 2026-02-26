package org.kirsch.util.distance;

import org.kirsch.model.gcs.Coordinate;
import org.kirsch.model.gcs.LatLng;
import org.kirsch.model.gcs.Length;

abstract class DistanceCalculator implements IDistanceCalculator {

  abstract DistanceCalculator build();
  public abstract Length approxDistance(LatLng p0, LatLng p1);
  abstract Length relativeGapDist(LatLng p0, LatLng p1, Length flatGap);
  public abstract LatLng findNextTarget(LatLng p0, LatLng p1, Length flatGap);

  public LatLng getCenter(LatLng p0, LatLng p1) {
    double latMid = (p0.getLatitude().toDegrees() + p1.getLatitude().toDegrees()) / 2;
    double lngMid = (p0.getLongitude().toDegrees() + p1.getLongitude().toDegrees()) / 2;

    return LatLng.builder()
        .latitude(Coordinate.fromDegrees(latMid))
        .longitude(Coordinate.fromDegrees(lngMid))
        .build();
  }

  protected static double distance(Coordinate c0, Coordinate c1) {
    return Math.sqrt(Math.pow(c0.toDegrees() - c1.toDegrees(), 2));
  }

}