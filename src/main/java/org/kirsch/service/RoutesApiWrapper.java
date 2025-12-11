package org.kirsch.service;

import com.google.api.gax.rpc.FixedHeaderProvider;
import com.google.api.gax.rpc.HeaderProvider;
import com.google.maps.routing.v2.ComputeRoutesRequest;
import com.google.maps.routing.v2.Route;
import com.google.maps.routing.v2.RoutesClient;
import com.google.maps.routing.v2.RoutesSettings;
import com.google.maps.routing.v2.Waypoint;
import java.util.List;
import org.kirsch.ApplicationProperties;


public class RoutesApiWrapper {

  private static final String FIELD_MASK_HEADER = "X-Goog-FieldMask";
  private static final String FIELD_MASK_VALUE = "routes.duration,routes.distanceMeters,routes.polyline.encodedPolyline";

  public void doGet(Waypoint origin, Waypoint destination) {

    String apiKey = ApplicationProperties.getInstance().getGoogleApiKey();

    try {

      HeaderProvider headerProvider = FixedHeaderProvider.create(
          FIELD_MASK_HEADER, FIELD_MASK_VALUE
      );

      RoutesSettings routesSettings = RoutesSettings.newBuilder()
          .setApiKey(apiKey)
          .setHeaderProvider(headerProvider)
          .build();

      try (RoutesClient routesClient = RoutesClient.create(routesSettings)) {


        ComputeRoutesRequest request = ComputeRoutesRequest.newBuilder()
            .setOrigin(origin)
            .setDestination(destination)
            // .setTravelMode(TravelMode.DRIVE)
            .build();

        List<Route> responseRoutes = routesClient.computeRoutes(request).getRoutesList();

        if (!responseRoutes.isEmpty()) {
          Route route = responseRoutes.get(0);
          System.out.println("Route found:");
          System.out.println("Duration: " + route.getDuration());
          System.out.println("Distance Meters: " + route.getDistanceMeters());
          System.out.println("Encoded Polyline: " + route.getPolyline().getEncodedPolyline());
        } else {
          System.out.println("No routes found.");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}