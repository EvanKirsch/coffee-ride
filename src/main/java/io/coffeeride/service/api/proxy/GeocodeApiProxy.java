package io.coffeeride.service.api.proxy;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import java.io.IOException;
import org.springframework.stereotype.Component;

// wrapping static methods in proxy to allow for mocking
@Component
public class GeocodeApiProxy implements IGeocodeApiProxy {

  public GeocodingResult[] geocode(GeoApiContext context, String address)
      throws IOException, InterruptedException, ApiException {
    return GeocodingApi.geocode(context,address).await();
  }

}