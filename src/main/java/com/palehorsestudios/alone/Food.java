package com.palehorsestudios.alone;

public enum Food {
  FISH("fish", 0.84, 3401.94),
  SQUIRREL("squirrel", 1.20, 340.1943),
  RABBIT("rabbit", 1.36, 907.185),
  MOOSE("moose", 1.02, 226796),
  PORCUPINE("porcupine", 1.52, 9071.85),
  BUG("bug", 5.00, 100),
  MUSHROOM("mushroom", 0.28, 210),
  BERRIES("berries", 0.57, 450);

  private final String name;
  private final double caloriesPerGram;
  private final double grams;

  private Food(String name, double caloriesPerGram, double grams) {
    this.name = name;
    this.caloriesPerGram = caloriesPerGram;
    this.grams = grams;
  }

  public double getCaloriesPerGram() {
    return this.caloriesPerGram;
  }

  public double getGrams() {
    return grams;
  }

  public String toString() {
    return this.name;
  }
}
