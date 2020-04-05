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

}
