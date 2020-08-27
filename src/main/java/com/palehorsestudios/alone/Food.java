package com.palehorsestudios.alone;

public enum Food {
    FISH(0.84),
    SQUIRREL(1.20),
    RABBIT(1.36),
    MOOSE(1.02),
    SNAKE(0.93),
    BUG(5.00),
    MUSHROOM(0.28),
    BERRIES(0.57);

    private final double caloriesPerGram;

    public double getCaloriesPerGram() {
        return this.caloriesPerGram;
    }

    private Food(double caloriesPerGram) {
        this.caloriesPerGram = caloriesPerGram;
    }

}
