package io.coffeeride.service.api;

import com.google.maps.GeoApiContext;
import com.google.maps.model.GeocodingResult;
import io.coffeeride.model.gcs.Coordinate;
import io.coffeeride.model.gcs.LatLng;
import io.coffeeride.service.api.proxy.IGeocodeApiProxy;
import io.coffeeride.util.ApplicationProperties;
import io.coffeeride.util.exception.CoffeeRideApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeocodeApiWrapper implements IGeocodeApiWrapper {

  private final String apiKey = ApplicationProperties.getInstance().getGoogleJavaApiKey();

  private final IGeocodeApiProxy proxy;

  @Autowired
  GeocodeApiWrapper(IGeocodeApiProxy proxy) {
    this.proxy = proxy;
  }

  public LatLng geocode(String address) throws CoffeeRideApiException {
    LatLng latLng;
    GeoApiContext context = new GeoApiContext.Builder()
        .apiKey(apiKey)
        .build();

    GeocodingResult[] response;
    try {
      response = proxy.geocode(context, address);

      latLng = LatLng.builder()
          .latitude(Coordinate.fromDegrees(response[0].geometry.location.lat))
          .longitude(Coordinate.fromDegrees(response[0].geometry.location.lng))
          .build();

    } catch (Exception e) {
      throw new CoffeeRideApiException("Failed Google API call to Geocode \"" + address + "\".", e);
    } finally {
      context.shutdown();
    }

    return latLng;
  }

}