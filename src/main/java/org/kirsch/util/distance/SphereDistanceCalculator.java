package org.kirsch.util.distance;

import org.kirsch.model.gcs.Coordinate;
import org.kirsch.model.gcs.LatLng;
import org.kirsch.model.gcs.Length;
import org.springframework.stereotype.Component;

// notes on distance calculations on earth: https://www.movable-type.co.uk/scripts/gis-faq-5.1.html
// package public to force interface use outside of package
@Component
final class SphereDistanceCalculator extends DistanceCalculator {

  private static final int EARTH_RADIUS_METERS = 6371000;

  public SphereDistanceCalculator build() {
    return new SphereDistanceCalculator();
  }

  @Override
  public Length approxDistance(LatLng p0, LatLng p1) {
    return this.haversineImpl(p0, p1);
  }

  Length haversineImpl(LatLng p0, LatLng p1) {
    double dPhi = distance(p0.getLatitude(), p1.getLatitude());
    double dLam = distance(p0.getLongitude(), p1.getLongitude());

    double havPhi = Math.pow(Math.sin(Math.toRadians(dPhi / 2)), 2);
    double havLam = Math.pow(Math.sin(Math.toRadians(dLam / 2)), 2);
    double cPhi0 = Math.cos(p0.getLatitude().toRadians());
    double cPhi1 = Math.cos(p1.getLatitude().toRadians());

    double havTheta = havPhi + (cPhi0 * cPhi1 * havLam);
    double theta = 2 * Math.asin(Math.min(1, Math.sqrt(havTheta)));

    return Length.fromMeters(theta * EARTH_RADIUS_METERS);
  }

  Length relativeGapDist(LatLng p0, LatLng p1, Length step) {
    double avgLat = (p0.getLatitude().toDegrees() + p1.getLatitude().toDegrees()) / 2;

    double latDist = distance(p0.getLatitude(), p1.getLatitude());
    double lngDist = distance(p0.getLongitude(), p1.getLongitude());

    double distance = (latDist + lngDist) == 0 ? Double.MIN_VALUE : (latDist + lngDist); // prevent divide by 0

    double uvLat = latDist / distance;
    double uvLng = lngDist / distance;

    double partLat = uvLat * step.toMeters();
    double partLng = uvLng * step.toMeters() * Math.cos(Math.toRadians(avgLat));

    return Length.fromMeters(Math.abs(partLat) + Math.abs(partLng));
  }

  // TODO - test
  public LatLng findNextTarget(LatLng p0, LatLng p1, Length flatGap) {
    double vLat = p0.getLatitude().toDegrees() - p1.getLatitude().toDegrees();
    double vLng = p0.getLongitude().toDegrees() - p1.getLongitude().toDegrees();
    double approxLatLngDist = approxDistance(p0, p1).toMeters();
    double approxGapDist = relativeGapDist(p0, p1, flatGap).toMeters();

    if (approxGapDist >= approxLatLngDist) {
      return p1;
    } else {
      double uvLat = ((vLat / approxLatLngDist) * approxGapDist);
      double uvLng = ((vLng / approxLatLngDist) * approxGapDist);
      return LatLng.builder()
          .latitude(Coordinate.fromDegrees(p0.getLatitude().toDegrees() - uvLat))
          .longitude(Coordinate.fromDegrees(p0.getLongitude().toDegrees() - uvLng))
          .build();
    }
  }

}