package com.palehorsestudios.alone.player;

public enum ActivityLevel {
  LOW(37.5, 75, 150),
  MEDIUM(75, 150, 300),
  HIGH(150, 300, 600);
  private final double caloriesBurnedPerHourLow;
  private final double caloriesBurnedPerHourMid;
  private final double caloriesBurnedPerHourHigh;

  private ActivityLevel(
      double caloriesBurnedPerHourLow,
      double caloriesBurnedPerHourMid,
      double caloriesBurnedPerHourHigh) {
    this.caloriesBurnedPerHourLow = caloriesBurnedPerHourLow;
    this.caloriesBurnedPerHourMid = caloriesBurnedPerHourMid;
    this.caloriesBurnedPerHourHigh = caloriesBurnedPerHourHigh;
  }

  public double getCaloriesBurned(SuccessRate successRate) {
    if (successRate == SuccessRate.LOW) {
      return caloriesBurnedPerHourLow;
    } else if (successRate == SuccessRate.MEDIUM) {
      return caloriesBurnedPerHourMid;
    } else {
      return caloriesBurnedPerHourHigh;
    }
  }
}
