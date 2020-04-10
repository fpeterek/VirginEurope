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

  public static CrewRole fromString(String str) {
    String lower = str.toLowerCase();

    if (lower.equals("leader")) {
      return Leader;
    } else if (lower.equals("safety")) {
      return Safety;
    } else if (lower.equals("attendant")) {
      return Attendant;
    }
    throw new IllegalArgumentException("Invalid enum value: " + str);

  }

}
