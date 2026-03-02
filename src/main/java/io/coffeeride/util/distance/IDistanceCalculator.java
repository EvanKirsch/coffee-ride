package io.coffeeride.util.distance;

import io.coffeeride.model.gcs.LatLng;
import io.coffeeride.model.gcs.Length;

public interface IDistanceCalculator {

  Length approxDistance(LatLng p0, LatLng p1);
  LatLng getCenter(LatLng p0, LatLng p1);
  LatLng findNextTarget(LatLng p0, LatLng p1, Length stepMeters);

}