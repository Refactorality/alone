package com.palehorsestudios.alone;

public enum Food {
  FISH("fish", 0.84),
  SQUIRREL("squirrel", 1.20),
  RABBIT("rabbit", 1.36),
  MOOSE("moose", 1.02),
  SNAKE("snake", 0.93),
  BUG("bug", 5.00),
  MUSHROOM("mushroom", 0.28),
  BERRIES("berries", 0.57);

  private final String name;
  private final double caloriesPerGram;

  private Food(String name, double caloriesPerGram) {
    this.name = name;
    this.caloriesPerGram = caloriesPerGram;
  }

  public double getCaloriesPerGram() {
    return this.caloriesPerGram;
  }

  public String toString() {
    return this.name;
  }
}
