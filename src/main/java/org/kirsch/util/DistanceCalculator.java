package org.kirsch.util;

import com.google.type.LatLng;

public final class DistanceCalculator {

  public static LatLng approxCenter(LatLng p0, LatLng p1) {
    double latMid = (p0.getLatitude() + p1.getLatitude()) / 2;
    double lngMid = (p0.getLongitude() + p1.getLongitude()) / 2;

    return LatLng.newBuilder()
        .setLatitude(latMid)
        .setLongitude(lngMid)
        .build();
  }

  public static long approxCrowDistance(LatLng p0, LatLng p1) {
    double latDif = p0.getLatitude() - p1.getLatitude();
    double lngDif = p0.getLongitude() - p1.getLongitude();
    double latLngDist = Math.sqrt(Math.pow(latDif, 2) + Math.pow(lngDif, 2));

    return Math.round(latLngDist * 111111.1);
  }

  public static LatLng findNextTarget(LatLng p0, LatLng p1, double flatGap) {
    double vLat = p0.getLatitude() - p1.getLatitude();
    double vLng = p0.getLongitude() - p1.getLongitude();
    double latDif = p0.getLatitude() - p1.getLatitude();
    double lngDif = p0.getLongitude() - p1.getLongitude();
    double latLngDist = Math.sqrt(Math.pow(latDif, 2) + Math.pow(lngDif, 2));
    //double approxDegreeGap = flatGap / 69.1;
    double approxDegreeGap = approxGapSizeFromMilesToDegrees(p0, p1, flatGap);

    if (approxDegreeGap >= latLngDist) {
      return p1;
    } else {
      double uvLat = ((vLat / latLngDist) * approxDegreeGap);
      double uvLng = ((vLng / latLngDist) * approxDegreeGap);
      return LatLng.newBuilder()
          .setLatitude(p0.getLatitude() - uvLat)
          .setLongitude(p0.getLongitude() - uvLng)
          .build();
    }
  }

  @Deprecated // this is not calculating correctly and needs to be fixed
  private static double approxGapSizeFromMilesToDegrees(LatLng p0, LatLng p1, double flatGap) {
    double avgLat = (p0.getLatitude() + p1.getLatitude()) / 2;

    double cnvFactLat = 69.1 * Math.cos(avgLat);
    double cnvFactLng = 69.1;

    double vpLat = Math.pow(p0.getLatitude() - p1.getLatitude(), 2);
    double vpLng = Math.pow(p0.getLongitude() - p1.getLongitude(), 2);

    double uvLat = 0;
    if (vpLat != 0 && cnvFactLat != 0) {
      uvLat = (Math.sqrt(vpLng / vpLat) * flatGap) / cnvFactLat;
    }

    double uvLng;
    if (vpLat != 0) {
      uvLng = ((1 - Math.sqrt(vpLng / vpLat)) * flatGap) / cnvFactLng;
    } else {
      uvLng = flatGap / cnvFactLng;
    }

    return uvLat + uvLng;
  }

}