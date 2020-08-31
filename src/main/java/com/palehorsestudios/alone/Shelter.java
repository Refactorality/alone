package com.palehorsestudios.alone;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Shelter {
  public static final int MAX_WATER = 10;
  public static final int MIN_WATER = 0;
  public static final double MAX_INTEGRITY = 10;
  public static final double MIN_INTEGRITY = 0;
  private final Map<Food, Double> foodCache;
  private final Map<Item, Integer> equipment;
  private double integrity;
  private double firewood;
  private int waterTank;

  public Shelter() {
    this.foodCache = new HashMap<>();
    this.equipment = new HashMap<>();
  }

  public double getIntegrity() {
    return integrity;
  }

  public void setIntegrity(double integrity) {
    this.integrity = integrity < MIN_INTEGRITY ? MIN_INTEGRITY : Math.min(integrity, MAX_INTEGRITY);
  }

  public double getFirewood() {
    return firewood;
  }

  public void setFirewood(int firewood) {
    this.firewood = firewood;
  }

  public int getWaterTank() {
    return waterTank;
  }

  public int updateWater(int water) {
    int currentWater = getWaterTank();
    int addedWater = 0;
    waterTank += water;
    if (waterTank >= MAX_WATER) {
      waterTank = MAX_WATER;
      return addedWater = MAX_WATER - currentWater;
    }
    else if (waterTank < MIN_WATER) {
      waterTank = MIN_WATER;
    }
    else {
      addedWater = water;
    }
    return addedWater;
  }

  public ImmutableMap<Food, Double> getFoodCache() {
    return ImmutableMap.copyOf(foodCache);
  }

  public Result addFoodToCache(Food food, double quantity) {
    Result.Builder resultBuilder = new Result.Builder();
    Optional<Double> currentQuantity = Optional.ofNullable(this.foodCache.get(food));
    if (currentQuantity.isPresent()) {
      this.foodCache.put(food, currentQuantity.get() + quantity);
    } else {
      this.foodCache.put(food, quantity);
    }
    return resultBuilder
        .food(food)
        .foodCount(quantity)
        .message(quantity + " grams of " + food + " added to your food cache.")
        .build();
  }

  public Result removeFoodFromCache(Food food, double quantity) {
    Result.Builder resultBuilder = new Result.Builder();
    Optional<Double> currentQuantity = Optional.ofNullable(this.foodCache.get(food));
    resultBuilder.food(food);
    if (currentQuantity.isPresent()) {
      if (currentQuantity.get() < quantity) {
        this.foodCache.put(food, 0.0);
        resultBuilder
            .foodCount(currentQuantity.get())
            .message(
                "You were only able to find "
                    + currentQuantity.get()
                    + " grams of "
                    + food
                    + " in your food cache.");
      } else {
        this.foodCache.put(food, currentQuantity.get() - quantity);
        resultBuilder
            .foodCount(quantity)
            .message("You removed " + quantity + " grams of " + food + " from your food cache.");
      }
    } else {
      resultBuilder.foodCount(0.0).message("You do not have any " + food + " in your food cache.");
    }
    return resultBuilder.build();
  }

  public ImmutableMap<Item, Integer> getEquipment() {
    return ImmutableMap.copyOf(equipment);
  }

  public Result addEquipment(Item item, int quantity) {
    Result.Builder resultBuilder = new Result.Builder();
    Optional<Integer> currentQuantity = Optional.ofNullable(this.equipment.get(item));
    if (currentQuantity.isPresent()) {
      this.equipment.put(item, currentQuantity.get() + quantity);
    } else {
      this.equipment.put(item, quantity);
    }
    return resultBuilder
        .item(item)
        .itemCount(quantity)
        .message(quantity + " " + item + " added to shelter")
        .build();
  }

  public Result removeEquipment(Item item, int quantity) {
    Result.Builder resultBuilder = new Result.Builder();
    Optional<Integer> currentQuantity = Optional.ofNullable(this.equipment.get(item));
    resultBuilder.item(item);
    if (currentQuantity.isPresent() && currentQuantity.get() > 0) {
      if (currentQuantity.get() < quantity) {
        this.equipment.put(item, 0);
        resultBuilder
            .itemCount(currentQuantity.get())
            .message("You only had " + currentQuantity.get() + " " + item + " in your shelter.");
      } else {
        this.equipment.put(item, currentQuantity.get() - quantity);
        resultBuilder
            .itemCount(quantity)
            .message(
                quantity
                    + " "
                    + item
                    + " removed from your shelter. You have "
                    + (currentQuantity.get() - quantity)
                    + " remaining.");
      }
    } else {
      resultBuilder.itemCount(0).message("You do not have a(n) " + item + " in your shelter.");
    }
    return resultBuilder.build();
  }

  @Override
  public String toString() {
    return "Shelter{"
        + "foodCache="
        + foodCache
        + ", equipment="
        + equipment
        + ", integrity="
        + integrity
        + ", firewood="
        + firewood
        + ", waterTank="
        + waterTank
        + '}';
  }
}
