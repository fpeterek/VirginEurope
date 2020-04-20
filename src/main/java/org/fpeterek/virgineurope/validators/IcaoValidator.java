package org.fpeterek.virgineurope.validators;

import java.util.regex.Pattern;

public class IcaoValidator {

  private IcaoValidator() { }

  public static boolean isValid(String icao) {
    return Pattern.matches("[A-Z]{4}", icao);
  }

}
