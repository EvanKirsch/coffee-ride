package io.coffeeride.service.api;

import com.google.api.gax.rpc.FixedHeaderProvider;
import com.google.api.gax.rpc.HeaderProvider;
import com.google.maps.routing.v2.ComputeRoutesRequest;
import com.google.maps.routing.v2.PolylineQuality;
import com.google.maps.routing.v2.Route;
import com.google.maps.routing.v2.RouteTravelMode;
import com.google.maps.routing.v2.RoutesClient;
import com.google.maps.routing.v2.RoutesSettings;
import com.google.maps.routing.v2.RoutingPreference;
import com.google.maps.routing.v2.Waypoint;
import io.coffeeride.adaptors.GoogleRouteAdaptor;
import io.coffeeride.adaptors.PlaceAdaptor;
import io.coffeeride.adaptors.RouteAdaptor;
import io.coffeeride.model.gcs.LatLng;
import io.coffeeride.util.ApplicationProperties;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class RoutesApiWrapper implements IRoutesApiWrapper {

  private static final Logger log = LoggerFactory.getLogger(RoutesApiWrapper.class);
  private static final String FIELD_MASK_HEADER = "X-Goog-FieldMask";
  private static final String FIELD_MASK_VALUE = "routes.duration,routes.distanceMeters,routes.polyline,routes.legs";

  private final ConversionService conversionService;

  @Autowired
  public RoutesApiWrapper(ConversionService conversionService) {
    this.conversionService = conversionService;
  }

  @Override
  public List<RouteAdaptor> computeRoute(LatLng origin, LatLng destination,
      List<PlaceAdaptor> intermediates) {
    List<RouteAdaptor> routes = new ArrayList<>();
    String apiKey = ApplicationProperties.getInstance().getGoogleJavaApiKey();

    try {

      HeaderProvider headerProvider = FixedHeaderProvider.create(
          FIELD_MASK_HEADER, FIELD_MASK_VALUE
      );

      RoutesSettings routesSettings = RoutesSettings.newBuilder()
          .setApiKey(apiKey)
          .setHeaderProvider(headerProvider)
          .build();

      try (RoutesClient routesClient = RoutesClient.create(routesSettings)) {

        List<Waypoint> waypoints = intermediates.stream()
            .map(elt -> conversionService.convert(elt.getLocation(), Waypoint.class))
            .toList();

        ComputeRoutesRequest request = ComputeRoutesRequest.newBuilder()
            .setOrigin(conversionService.convert(origin, Waypoint.class))
            .setDestination(conversionService.convert(destination, Waypoint.class))
            .addAllIntermediates(waypoints)
            .setRoutingPreference(RoutingPreference.ROUTING_PREFERENCE_UNSPECIFIED)
            .setComputeAlternativeRoutes(false)
            .setPolylineQuality(PolylineQuality.OVERVIEW)
            .setTravelMode(RouteTravelMode.BICYCLE)
            .build();

        List<Route> responseRoutes = routesClient.computeRoutes(request).getRoutesList();
        responseRoutes.forEach(elt -> routes.add(new GoogleRouteAdaptor(elt)));

      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return routes;
  }

}