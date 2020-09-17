package com.palehorsestudios.alone;

import java.util.Objects;

public class Food extends Item {
  private double caloriesPerGram;
  private double grams;

  public Food() {

  }

  public Food(String name, double caloriesPerGram, double grams) {
    super(name);
    this.caloriesPerGram = caloriesPerGram;
    this.grams = grams;
  }

  public double getCaloriesPerGram() {
    return caloriesPerGram;
  }

  public void setCaloriesPerGram(double caloriesPerGram) {
    this.caloriesPerGram = caloriesPerGram;
  }

  public double getGrams() {
    return grams;
  }

  public void setGrams(double grams) {
    this.grams = grams;
  }

  @Override
  public String toString() {
    return super.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Food food = (Food) o;
    return Double.compare(food.caloriesPerGram, caloriesPerGram) == 0 &&
            Double.compare(food.grams, grams) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), caloriesPerGram, grams);
  }
}
