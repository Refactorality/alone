package com.palehorsestudios.alone;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Shelter {
  private final Map<Food, Double> foodCache;
  private final Map<Item, Integer> equipment;
  private int integrity;
  private int firewood;

  public Shelter() {
    this.foodCache = new HashMap<>();
    this.equipment = new HashMap<>();
  }

  public int getIntegrity() {
    return integrity;
  }

  public void setIntegrity(int integrity) {
    this.integrity = integrity;
  }

  public int getFirewood() {
    return firewood;
  }

  public void setFirewood(int firewood) {
    this.firewood = firewood;
  }

  public ImmutableMap<Food, Double> getFoodCache() {
    return ImmutableMap.copyOf(foodCache);
  }

  public Result addFood(Food food, double quantity) {
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

  public Result removeFood(Food food, double quantity) throws IllegalFoodRemovalException {
    Result.Builder resultBuilder = new Result.Builder();
    Optional<Double> currentQuantity = Optional.ofNullable(this.foodCache.get(food));
    if (currentQuantity.isPresent()) {
      if (currentQuantity.get() < quantity) {
        throw new IllegalFoodRemovalException(
            "You tried to remove "
                + quantity
                + " of "
                + food
                + ", but you only have "
                + currentQuantity
                + ".");
      }
      this.foodCache.put(food, currentQuantity.get() - quantity);
      resultBuilder
              .food(food)
              .foodCount(quantity)
              .message("You removed " + quantity + " grams of " + food + " from your food cache.");
    } else {
      throw new IllegalFoodRemovalException("You do not have any " + food + ".");
    }
    return resultBuilder.build();
  }

  public ImmutableMap<Item, Integer> getEquipment() {
    return ImmutableMap.copyOf(equipment);
  }

  public void addEquipment(Item item, int quantity) {
    int currentQuantity = this.equipment.get(item);
    this.equipment.put(item, currentQuantity + quantity);
  }

  public void removeEquipment(Item item, int quantity) throws IllegalEquipmentRemovalException {
    int currentQuantity = this.equipment.get(item);
    if (currentQuantity < quantity) {
      throw new IllegalEquipmentRemovalException(
          "You tried to remove "
              + quantity
              + " of "
              + item
              + ", but you only have "
              + currentQuantity
              + ".");
    }
    this.equipment.put(item, currentQuantity - quantity);
  }

  @Override
  public String toString() {
    return "Shelter{" +
            "foodCache=" + foodCache +
            ", equipment=" + equipment +
            ", integrity=" + integrity +
            ", firewood=" + firewood +
            '}';
  }
}
