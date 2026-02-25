package org.kirsch.model.gcs;

/*
 * Problem: 
 *   Coordinates can be represented in both degrees and radians. 
 *   I was often getting confused what unit my double was in.
 * Solution: 
 *   Wrap the double in a class and use .toDegrees() and .toRadians()
 *   to access the double. Stored in degrees because this is more intuitive 
 */
public final class Coordinate {

  private final double degrees;

  private Coordinate(double degrees) {
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