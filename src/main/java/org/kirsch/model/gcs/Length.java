package org.kirsch.model.gcs;

public class Length implements Comparable<Length> {

  private static final double METERS_PER_MILE = 1609.344;
  private static final double METERS_PER_KILOMETER = 1000;

  private final double meters;

  private Length(double meters) {
    this.meters = meters;
  }

  public double toMeters() {
    return meters;
  }

  public double toMiles() {
    return meters / METERS_PER_MILE;
  }

  public double toKilometers() {
    return meters / METERS_PER_KILOMETER;
  }

  public static Length fromMeters(double meters) {
    return new Length(meters);
  }

  public static Length fromMiles(double miles) {
    return new Length(miles * METERS_PER_MILE);
  }

  public static Length fromKilometers(double km) {
    return new Length(km * METERS_PER_KILOMETER);
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof Length
      && ((Length) o).meters == this.meters;
  }

  @Override
  public String toString() {
    return Double.toString(this.meters); 
  }

  @Override
  public int compareTo(Length that) {
    return Double.compare(this.meters, that.meters);
  }
    
}