package com.palehorsestudios.alone.activity;

import com.palehorsestudios.alone.player.SuccessRate;

public enum ActivityLevel {
  LOW(37.5, 75, 150, 1, 1, 1),
  MEDIUM(75, 150, 300, 1, 2, 2),
  HIGH(350, 700, 1300, 1, 2, 3);
  private final double caloriesBurnedPerHourLow;
  private final double caloriesBurnedPerHourMid;
  private final double caloriesBurnedPerHourHigh;
  private final int hydrationCostLow;
  private final int hydrationCostMid;
  private final int hydrationCostHigh;

  private ActivityLevel(
      double caloriesBurnedPerHourLow,
      double caloriesBurnedPerHourMid,
      double caloriesBurnedPerHourHigh,
      int hydrationCostLow,
      int hydrationCostMid,
      int hydrationCostHigh) {
    this.caloriesBurnedPerHourLow = caloriesBurnedPerHourLow;
    this.caloriesBurnedPerHourMid = caloriesBurnedPerHourMid;
    this.caloriesBurnedPerHourHigh = caloriesBurnedPerHourHigh;
    this.hydrationCostLow = hydrationCostLow;
    this.hydrationCostMid = hydrationCostMid;
    this.hydrationCostHigh = hydrationCostHigh;
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

  public int getHydrationCost(SuccessRate successRate) {
    if (successRate == SuccessRate.LOW) {
      return hydrationCostLow;
    } else if (successRate == SuccessRate.MEDIUM) {
      return hydrationCostMid;
    } else {
      return hydrationCostHigh;
    }
  }
}
