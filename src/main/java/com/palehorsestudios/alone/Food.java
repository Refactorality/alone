package com.palehorsestudios.alone;

public class Food {
  private String name;
  private String foodName;
  private double caloriesPerGram;
  private double grams;

  public Food() {

  }

  public Food(String name, double caloriesPerGram, double grams) {
    this.foodName = name;
    this.caloriesPerGram = caloriesPerGram;
    this.grams = grams;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFoodName() {
    return foodName;
  }

  public void setFoodName(String foodName) {
    this.foodName = foodName;
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
    return this.foodName;
  }
}
