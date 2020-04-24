package org.fpeterek.virgineurope.common;

public enum EtopsCertified {
  Yes("Yes"), No("No"), NA("N/A");

  private String cert;

  EtopsCertified(String str) {
    cert = str;
  }

  public static EtopsCertified fromString(String str) {
    String lower = str.toLowerCase();
    if (lower.equals("yes")) {
      return Yes;
    } else if (lower.equals("no")) {
      return No;
    } else if (lower.equals("na")) {
      return NA;
    }

    throw new IllegalArgumentException("Invalid enum value: " + str);

  }

  @Override
  public String toString() {
    return cert;
  }

  public String dbValue() {
    String lower = cert.toLowerCase();
    if (lower.equals("yes")) {
      return "yes";
    } else if (lower.equals("na")) {
      return "NA";
    }
    return "no";
  }

}
