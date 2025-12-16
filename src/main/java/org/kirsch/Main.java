package org.kirsch;

import com.google.type.LatLng;
import java.io.IOException;
import org.kirsch.service.pathfinding.IPathFinder;
import org.kirsch.service.pathfinding.SdtPathFinder;

public class Main {

  public static void main(String[] args) throws IOException {
    LatLng origin = createLatLng(args[0], args[1]);
    LatLng destination = createLatLng(args[2], args[3]);
    IPathFinder pathFinder = new SdtPathFinder();
    pathFinder.buildRoute(origin, destination);
  }

  private static LatLng createLatLng(String lat, String lng) {
    return LatLng.newBuilder()
        .setLatitude(Float.parseFloat(lat))
        .setLongitude(Float.parseFloat(lng))
        .build();
  }

}