package org.kirsch.util.distance;

import org.kirsch.model.gcs.LatLng;
import org.kirsch.model.gcs.Length;

public interface IDistanceCalculator {

  Length approxDistance(LatLng p0, LatLng p1);
  LatLng getCenter(LatLng p0, LatLng p1);
  LatLng findNextTarget(LatLng p0, LatLng p1, Length stepMeters);

}