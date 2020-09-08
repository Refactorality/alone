package com.palehorsestudios.alone;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HelperMethods {
  /**
   * Helper method for rounding double values. Thank you to https://www.baeldung.com/java-round-decimal-number
   * @param value Value to be rounded.
   * @param places Desired decimal places.
   * @return Rounded value.
   */
  public static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    BigDecimal bd = new BigDecimal(Double.toString(value));
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }
}
