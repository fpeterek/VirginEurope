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

}
