package org.kirsch.service.api;

import org.kirsch.model.gcs.LatLng;

public interface IGeocodeApiWrapper {

  LatLng geocode(String address);

}