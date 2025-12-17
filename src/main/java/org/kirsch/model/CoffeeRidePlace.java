package org.kirsch.model;

import com.google.maps.places.v1.Place;
import lombok.Data;

@Data
public class CoffeeRidePlace {

  private String displayName;
  private String address;
  private String name;

  public CoffeeRidePlace(Place place) {
    this.displayName = place.getDisplayName().getText();
    this.address = place.getFormattedAddress();
    this.name = place.getName();
  }

}