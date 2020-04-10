package org.fpeterek.virgineurope.common;

public enum SeatType {
  Window("Window"), Middle("Middle"), Aisle("Aisle");

  private String join;

  SeatType(String str) {
    join = str;
  }

  @Override
  public String toString() {
    return join;
  }

  public static SeatType fromString(String str) {

    String lower = str.toLowerCase();

    if (lower.equals("window")) {
      return Window;
    } else if (lower.equals("middle")) {
      return Middle;
    } else if (lower.equals("aisle")) {
      return Aisle;
    }

    throw new IllegalArgumentException("Invalid enum value: " + str);

  }

}
