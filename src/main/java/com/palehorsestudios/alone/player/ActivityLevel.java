package com.palehorsestudios.alone.player;

public enum ActivityLevel {
    LOW(500),
    MEDIAN(1000),
    HIGH(1500);

    private final double caloriesBurnedPerHour;

    public double getCaloriesBurnedPerHour() {
        return this.caloriesBurnedPerHour;
    }

    private ActivityLevel(double caloriesBurnedPerHour) {
        this.caloriesBurnedPerHour = caloriesBurnedPerHour;
    }
}
