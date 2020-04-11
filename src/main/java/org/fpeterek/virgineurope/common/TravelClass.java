package org.fpeterek.virgineurope.common;

public enum TravelClass {
  Economy("Economy"), Business("Business"), First("First");

  private String cls;

  TravelClass(String str) {
    cls = str;
  }

  @Override
  public String toString() {
    return cls;
  }

  public static TravelClass fromString(String str) {

    String lower = str.toLowerCase();

    if (lower.equals("economy")) {
      return Economy;
    } else if (lower.equals("business")) {
      return Business;
    } else if (lower.equals("first")) {
      return First;
    }

    throw new IllegalArgumentException("Invalid enum value: " + str);

  }

}
