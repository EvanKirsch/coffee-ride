package io.coffeeride.model;

import com.google.type.LatLng;
import io.coffeeride.adaptors.PlaceAdaptor;
import lombok.Data;

@Data
public final class CoffeeRidePlace {

  private final String displayName;
  private final String address;
  private final double lat;
  private final double lng;
  private final String name;

  public CoffeeRidePlace(PlaceAdaptor place) {
    this.displayName = place.getDisplayName();
    this.address = place.getAddress();
    this.lat = place.getLatitude();
    this.lng = place.getLongitude();
    this.name = place.getName();
  }

  public CoffeeRidePlace(LatLng latLng) {
    this.displayName = "";
    this.address = "";
    this.lat = latLng.getLatitude();
    this.lng = latLng.getLongitude();
    this.name = "";
  }

}