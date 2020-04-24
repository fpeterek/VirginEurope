package org.fpeterek.virgineurope.common;

public enum Seniority {
  Junior("Junior"), Senior("Senior");

  private String seniority;

  Seniority(String str) {
    seniority = str;
  }

  @Override
  public String toString() {
    return seniority;
  }

  public static Seniority fromString(String str) {

    String lower = str.toLowerCase();

    if (lower.equals("junior")) {
      return Junior;
    } else if (lower.equals("senior")) {
      return Senior;
    }

    throw new IllegalArgumentException("Invalid enum value: " + str);

  }

  public String dbValue() {
    return seniority.toLowerCase();
  }

}
