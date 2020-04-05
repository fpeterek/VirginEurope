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
}
