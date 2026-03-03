package io.coffeeride.model.gcs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/*
 * Problem: 
 *   Coordinates can be represented in both degrees and radians. 
 *   I was often getting confused what unit my double was in.
 * Solution: 
 *   Wrap the double in a class and use .toDegrees() and .toRadians()
 *   to access the double. Stored in degrees because this is more intuitive 
 */
@Data    // provides getter/setter for serialization.
         // This would probably be better done by overriding serializable methods
public final class Coordinate {

  private final double degrees;

  private Coordinate(double degrees) {
    this.degrees = degrees;
  }

  @JsonCreator
  public Coordinate(@JsonProperty("degrees") Double degrees) {
    this.degrees = degrees;
  }

  public double toDegrees() {
    return degrees;
  }

  public double toRadians() {
    return Math.toRadians(degrees);
  }

  public static Coordinate fromDegrees(double degrees) {
    return new Coordinate(degrees);
  }

  public static Coordinate fromRadians(double radians) {
    return new Coordinate(Math.toDegrees(radians));
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof Coordinate
        && ((Coordinate) o).degrees == this.degrees;
  }

  @Override
  public String toString() {
    return String.valueOf(degrees);
  }

}