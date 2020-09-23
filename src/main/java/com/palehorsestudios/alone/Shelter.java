package com.palehorsestudios.alone;

import com.google.common.collect.ImmutableMap;
import com.palehorsestudios.alone.util.StatTracker;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** Player's shelter to protect from environment and store food, water, and firewood. */
public class Shelter {
  // static variables
  private static final int MAX_WATER = 10;
  private static final int MIN_WATER = 0;
  private static final double MAX_INTEGRITY = 10;
  private static final double MIN_INTEGRITY = 0;
  private static final int MIN_FIREWOOD = 0;
  // instance variables
  private final Map<Food, Double> foodCache;
  private final Map<Item, Integer> equipment;
  private double integrity;
  private double firewood;
  private int waterTank;
  private boolean hasFire;

  // constructors

  /** Public constructor for Shelter. */
  public Shelter() {
    this.foodCache = new HashMap<>();
    this.equipment = new HashMap<>();
  }

  // getters and setters

  /**
   * Getter for Shelter integrity.
   *
   * @return Shelter integrity value.
   */
  public double getIntegrity() {
    return integrity;
  }

  /**
   * Setter for Shelter integrity.
   *
   * @param integrity New shelter integrity value. Value must be inclusively between {@value
   *     Shelter#MIN_INTEGRITY} and {@value Shelter#MAX_INTEGRITY}.
   */
  public void setIntegrity(double integrity) {
    this.integrity = integrity < MIN_INTEGRITY ? MIN_INTEGRITY : Math.min(integrity, MAX_INTEGRITY);
  }

  /**
   * Getter for Shelter firewood cache level.
   *
   * @return Shelter firewood cache level.
   */
  public double getFirewood() {
    return firewood;
  }

  /**
   * Setter for Shelter firewood.
   *
   * @param firewood Amount to add or subtract from Shelter firewood cache level.
   */
  public void updateFirewood(double firewood) {
    this.firewood = Math.max(this.firewood + firewood, MIN_FIREWOOD);
  }

  /**
   * Getter for Shelter water tank.
   *
   * @return Shelter water tank level.
   */
  public int getWaterTank() {
    return waterTank;
  }

  /**
   * Setter for Shelter water tank.
   *
   * @param water Value to be added or removed from water tank. Updated water tank value must be
   * inclusively between {@value Shelter#MIN_WATER} and {@value Shelter#MAX_WATER}.
   * @return Amount of water added or removed from water tank.
   */
  public int updateWater(int water) {
    int currentWater = getWaterTank();
    int addedWater = 0;
    waterTank += water;
    if (waterTank >= MAX_WATER) {
      waterTank = MAX_WATER;
      addedWater = MAX_WATER - currentWater;
    } else if (waterTank < MIN_WATER) {
      waterTank = MIN_WATER;
    } else {
      addedWater = water;
    }
    return addedWater;
  }

  /**
   * Getter for Shelter food cache.
   *
   * @return foodCache Map.
   */
  public Map<Food, Double> getFoodCache() {
    return foodCache;
  }

  /**
   * Getter for Shelter equipment.
   *
   * @return Immutable copy of Shelter equipment.
   */
//  public ImmutableMap<Item, Integer> getEquipment() {
//    return ImmutableMap.copyOf(equipment);
//  }
  public Map<Item, Integer> getEquipment() {
    return equipment;
  }

  /**
   * Getter for determining if a fire is lit in the Shelter.
   * @return true if fire is lit, false if not.
   */
  public boolean hasFire() {
    return hasFire;
  }

  /**
   * Setter for Shelter fire.
   * @param fire true to light a fire, false to put it out.
   */
  public void setFire(boolean fire) {
    this.hasFire = fire;
  }

  // business methods

  /**
   * Method for adding Food to Shelter food cache.
   *
   * @param food Food to be added to food cache.
   * @param quantity Quantity of Food to be added.
   */
  public void addFoodToCache(Food food, double quantity) {
    Optional<Double> currentQuantity = Optional.ofNullable(this.foodCache.get(food));
    StatTracker.logFood(food, quantity);
    if (currentQuantity.isPresent()) {
      this.foodCache.put(food, currentQuantity.get() + quantity);
    } else {
      this.foodCache.put(food, quantity);
    }
  }

  /**
   * Method for removing Food from Shelter food cache.
   *
   * @param food Food to be removed from food cache.
   * @param quantity Quantity of Food to be removed.
   * @return Result of attempting to remove Food from the cache.
   */
  public double removeFoodFromCache(Food food, double quantity) {
    double removalQuantity;
    Optional<Double> currentQuantity = Optional.ofNullable(this.foodCache.get(food));
    if (currentQuantity.isPresent()) {
      if (currentQuantity.get() < quantity) {
        this.foodCache.put(food, 0.0);
        removalQuantity = currentQuantity.get();
      } else {
        this.foodCache.put(food, currentQuantity.get() - quantity);
        removalQuantity = quantity;
      }
    } else {
      removalQuantity = 0;
    }
    return removalQuantity;
  }



  /**
   * Method for adding Item(s) to Shelter equipment cache.
   *
   * @param item Item to be added to equipment cache.
   * @param quantity Quantity of Item to be added.
   */
  public void addEquipment(Item item, int quantity) {
    Optional<Integer> currentQuantity = Optional.ofNullable(this.equipment.get(item));
    if (currentQuantity.isPresent()) {
      this.equipment.put(item, currentQuantity.get() + quantity);
    } else {
      this.equipment.put(item, quantity);
    }
  }

  /**
   * Method for removing Item(s) from the Shelter equipment cache.
   *
   * @param item Item to be removed from the equipment cache.
   * @param quantity Quantity of Item to be removed.
   * @return Quantity of Item removed from equipment cache.
   */
  public int removeEquipment(Item item, int quantity) {
    int removalQuantity;
    Optional<Integer> currentQuantity = Optional.ofNullable(this.equipment.get(item));
    if (currentQuantity.isPresent() && currentQuantity.get() > 0) {
      if (currentQuantity.get() < quantity) {
        this.equipment.put(item, 0);
        removalQuantity = currentQuantity.get();
      } else {
        this.equipment.put(item, currentQuantity.get() - quantity);
        removalQuantity = quantity;
      }
    } else {
      removalQuantity = 0;
    }
    return removalQuantity;
  }

  // Object overrides

  /**
   * Shelter toString override.
   *
   * @return String representation of Shelter.
   */
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
