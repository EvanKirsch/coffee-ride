package org.kirsch.service.api;

import com.google.type.LatLng;

public interface IGeocodeApiWrapper {

  LatLng geocode(String address);

}