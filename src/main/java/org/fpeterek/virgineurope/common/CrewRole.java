package org.fpeterek.virgineurope.common;

public enum CrewRole {
  Leader("Leader"), Safety("Safety"), Attendant("Attendant");

  private String role;

  CrewRole(String str) {
    role = str;
  }

  @Override
  public String toString() {
    return role;
  }

}
