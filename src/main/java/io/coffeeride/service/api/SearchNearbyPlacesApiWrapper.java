package io.coffeeride.service.api;

import com.google.api.gax.rpc.FixedHeaderProvider;
import com.google.api.gax.rpc.HeaderProvider;
import com.google.maps.places.v1.Circle;
import com.google.maps.places.v1.Place;
import com.google.maps.places.v1.PlacesClient;
import com.google.maps.places.v1.PlacesSettings;
import com.google.maps.places.v1.SearchNearbyRequest;
import com.google.maps.places.v1.SearchNearbyResponse;
import io.coffeeride.adaptors.GooglePlaceAdaptor;
import io.coffeeride.adaptors.PlaceAdaptor;
import io.coffeeride.model.gcs.LatLng;
import io.coffeeride.util.ApplicationProperties;
import io.coffeeride.util.distance.IDistanceCalculator;
import io.coffeeride.util.distance.SphereDistanceCalculatorFactory;
import io.coffeeride.util.exception.CoffeeRideApiException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchNearbyPlacesApiWrapper implements ISearchNearbyPlacesApiWrapper {

  private static final Logger log = LoggerFactory.getLogger(SearchNearbyPlacesApiWrapper.class);
  private static final String FIELD_MASK_HEADER = "X-Goog-FieldMask";
  private static final String FIELD_MASK_VALUE = "*";

  private final String apiKey = ApplicationProperties.getInstance().getGoogleJavaApiKey();

  private final IDistanceCalculator distanceCalculator;

  @Autowired
  public SearchNearbyPlacesApiWrapper(SphereDistanceCalculatorFactory dcFactory) {
    this.distanceCalculator = dcFactory.getCalculator();
  }

  @Override
  public List<PlaceAdaptor> searchNearby(LatLng origin, LatLng destination)
      throws CoffeeRideApiException {
    List<PlaceAdaptor> places = new ArrayList<>();

    try {

      HeaderProvider headerProvider = FixedHeaderProvider.create(
          FIELD_MASK_HEADER, FIELD_MASK_VALUE
      );

      PlacesSettings placesSettings = PlacesSettings.newBuilder()
          .setApiKey(apiKey)
          .setHeaderProvider(headerProvider)
          .build();

      try (PlacesClient placesClient = PlacesClient.create(placesSettings)) {

        SearchNearbyRequest.LocationRestriction restriction = getLocationRestriction(origin,
            destination);

        SearchNearbyRequest request = SearchNearbyRequest.newBuilder()
            .addIncludedTypes("coffee_shop")
            .setLocationRestriction(restriction)
            .setMaxResultCount(ApplicationProperties.getInstance().getPlacesMaxResultCount())
            .build();

        SearchNearbyResponse searchNearbyResponse = placesClient.searchNearby(request);

        if (searchNearbyResponse.isInitialized()) {
          List<Place> googlePlaces = searchNearbyResponse.getPlacesList();
          googlePlaces.forEach(elt -> places.add(new GooglePlaceAdaptor(elt)));
        } else {
          throw new Exception("No Places Found");
        }
      }
    } catch (Exception e) {
      throw new CoffeeRideApiException("Failed Google API call to Find Nearby Places: " + e.getMessage(), e);
    }
    return places;
  }

  SearchNearbyRequest.LocationRestriction getLocationRestriction(LatLng origin,
      LatLng destination) {

    // rebuild in google LatLng obj
    com.google.type.LatLng ll = com.google.type.LatLng.newBuilder()
        .setLatitude(destination.getLatitude().toDegrees())
        .setLongitude(destination.getLongitude().toDegrees())
        .build();

    Circle searchArea = Circle.newBuilder()
        .setCenter(ll)
        .setRadius(distanceCalculator.approxDistance(origin, destination).toMeters())
        .build();

    return SearchNearbyRequest.LocationRestriction
        .newBuilder()
        .setCircle(searchArea)
        .build();

  }

}