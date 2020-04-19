package org.fpeterek.virgineurope.validators;

import java.util.regex.Pattern;

public class FlightNumberValidator {

  private FlightNumberValidator() { }

  public static boolean isValid(String flNo) {
    return Pattern.matches("VU[0-9]{4}", flNo);
  }

}
