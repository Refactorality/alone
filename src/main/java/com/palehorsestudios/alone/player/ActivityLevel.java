package com.palehorsestudios.alone.player;

import java.util.Random;

public enum ActivityLevel {
    LOW(50, 150, 200),
    MEDIAN(100, 200, 300),
    HIGH(300, 450, 600);
    private final double caloriesBurnedPerHourLow;
    private final double caloriesBurnedPerHourMid;
    private final double caloriesBurnedPerHourHigh;

    public double getCaloriesBurned(int successRate) {
        if (successRate == 1) {
            return caloriesBurnedPerHourLow;
        }
        else if (successRate == 2) {
            return caloriesBurnedPerHourMid;
        }
        else {
            return caloriesBurnedPerHourHigh;
        }
    }

    private ActivityLevel(double caloriesBurnedPerHourLow, double caloriesBurnedPerHourMid, double caloriesBurnedPerHourHigh) {
        this.caloriesBurnedPerHourLow = caloriesBurnedPerHourLow;
        this.caloriesBurnedPerHourMid = caloriesBurnedPerHourMid;
        this.caloriesBurnedPerHourHigh = caloriesBurnedPerHourHigh;
    }
}
