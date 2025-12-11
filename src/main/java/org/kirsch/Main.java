package org.kirsch;

import com.google.maps.routing.v2.*;
import com.google.type.LatLng;
import java.io.IOException;
import org.kirsch.service.RoutesApiWrapper;

public class Main {
  public static void main(String[] arguments) throws IOException {
    RoutesApiWrapper wrapper = new RoutesApiWrapper();
    Waypoint origin = createWaypointForLatLng(37.420761, -122.081356);
    Waypoint destination = createWaypointForLatLng(37.41767, -122.079595);
    wrapper.doGet(origin, destination);
  }

  private static Waypoint createWaypointForLatLng(double lat, double lng) {
    return Waypoint.newBuilder()
        .setLocation(Location.newBuilder()
            .setLatLng(LatLng.newBuilder()
                .setLatitude(lat)
                .setLongitude(lng)
                .build())
            .build())
        .build();
  }

}