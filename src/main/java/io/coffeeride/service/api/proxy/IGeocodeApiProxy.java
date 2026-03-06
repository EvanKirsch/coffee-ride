package io.coffeeride.service.api.proxy;

import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import java.io.IOException;

public interface IGeocodeApiProxy {

  GeocodingResult[] geocode(GeoApiContext context, String address)
      throws IOException, InterruptedException, ApiException;

}