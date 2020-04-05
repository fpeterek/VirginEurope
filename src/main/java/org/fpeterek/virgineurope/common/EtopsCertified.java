package org.fpeterek.virgineurope.common;

public enum EtopsCertified {
  Yes("Yes"), No("No"), NA("N/A");

  private String cert;

  EtopsCertified(String str) {
    cert = str;
  }

  @Override
  public String toString() {
    return cert;
  }
}
