package io.coffeeride.adaptors;

import com.google.maps.places.v1.Place;
import io.coffeeride.model.gcs.Coordinate;
import io.coffeeride.model.gcs.LatLng;

public final class GooglePlaceAdaptor implements PlaceAdaptor {

  private final Place place;

  public GooglePlaceAdaptor(Place place) {
    this.place = place;
  }

  public GooglePlaceAdaptor(com.google.type.LatLng latLng) {
    this.place = Place.newBuilder()
        .setLocation(latLng)
        .build();
  }

  public GooglePlaceAdaptor(LatLng latLng) {
    this.place = Place.newBuilder()
        .setLocation(com.google.type.LatLng.newBuilder()
            .setLatitude(latLng.getLatitude().toDegrees())
            .setLongitude(latLng.getLatitude().toDegrees())
            .build()
        )
        .build();

  }

  @Override
  public String getDisplayName() {
    return place.getDisplayName().getText();
  }

  @Override
  public String getAddress() {
    return place.getFormattedAddress();
  }

  @Override
  public double getLatitude() {
    return place.getLocation().getLatitude();
  }

  @Override
  public double getLongitude() {
    return place.getLocation().getLongitude();
  }

  @Override
  public String getName() {
    return place.getName();
  }

  @Override
  public LatLng getLocation() {
    return LatLng.builder()
        .latitude(Coordinate.fromDegrees(place.getLocation().getLatitude()))
        .longitude(Coordinate.fromDegrees(place.getLocation().getLongitude()))
        .build();
  }

}