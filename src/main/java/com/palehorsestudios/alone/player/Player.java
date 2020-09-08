package com.palehorsestudios.alone.player;

import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.Shelter;
import com.palehorsestudios.alone.activity.ActivityLevel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;

public class Player {
  // static constants
  private static final int MIN_HYDRATION = 0;
  private static final int MAX_HYDRATION = 10;
  private static final int MIN_WEIGHT = 0;
  private static final int MIN_MORALE = 0;
  private static final int MAX_MORALE = 10;
  private static final int SERVING_SIZE = 340;
  private static final double FIREWOOD_BUNDLE = 1;
  private static final double CALORIES_PER_POUND = 285.7;
  private static final int MAX_ITEM_CARRY_SIZE = 3;
  private final Set<Item> items;
  private final Shelter shelter;
  // instance vars
  private int hydration;
  private double weight;
  private int morale;

  /**
   * Public Player constructor.
   * @param items Set of Items to be added to Player Shelter.
   */
  public Player(Set<Item> items) {
    this.hydration = 5;
    this.weight = 180;
    this.morale = 5;
    this.items = new HashSet<>();
    this.shelter = new Shelter();
    for (Item item : items) {
      this.shelter.addEquipment(item, 1);
    }
    // boost shelter if Player has fire starting items
    if(items.contains(Item.FLINT_AND_STEEL) || items.contains(Item.MATCHES) || items.contains(Item.LIGHTER)) {
      this.shelter.setIntegrity(this.shelter.getIntegrity() + 1);
    }
    // boost shelter if Player has a tarp
    if(items.contains(Item.TARP)) {
      this.shelter.setIntegrity(this.shelter.getIntegrity() + 1);
    }
    // boost shelter if Player has items to assist in shelter construction
    if(items.contains(Item.PARACHUTE_CHORD) || items.contains(Item.AXE) || items.contains(Item.HATCHET) || items.contains(Item.SHOVEL) || items.contains(Item.KNIFE) || items.contains(Item.TARP)){
      this.shelter.setIntegrity(this.shelter.getIntegrity() + 1);
    }
    // boost shelter if Player has sleeping gear
    if(items.contains(Item.SLEEPING_GEAR)) {
      this.shelter.setIntegrity(this.shelter.getIntegrity() + 1);
    }
    // boost shelter if Player has other "nice to have" items
    if((items.contains(Item.FLASHLIGHT) && items.contains(Item.BATTERIES)) || items.contains(Item.POT) || items.contains(Item.SURVIVAL_MANUAL)) {
      this.shelter.setIntegrity(this.shelter.getIntegrity() + 1);
    }
  }

  // getters

  /**
   * Getter for Player hydration.
   *
   * @return Player hydration.
   */
  public int getHydration() {
    return hydration;
  }

  /**
   * Setter for Player hydration.
   *
   * @param hydration value for Player hydration.
   */
  public void setHydration(int hydration) {
    this.hydration = hydration < MIN_HYDRATION ? MIN_HYDRATION : Math.min(hydration, MAX_HYDRATION);
  }

  /**
   * Getter for Player weight.
   *
   * @return Player weight.
   */
  public double getWeight() {
    return weight;
  }

  /**
   * Setter for Player weight.
   *
   * @param weight value for Player weight.
   */
  public void setWeight(double weight) {
    this.weight = Math.max(MIN_WEIGHT, weight);
  }

  // setters

  /**
   * Getter for Player morale.
   *
   * @return Player morale.
   */
  public int getMorale() {
    return morale;
  }

  /**
   * Getter for items Player is currently carrying.
   *
   * @return ImmutableSet of Player items.
   */
  public Set<Item> getItems() {
    return this.items;
  }

  /**
   * Getter for Player's shelter.
   *
   * @return Player's shelter.
   */
  public Shelter getShelter() {
    return this.shelter;
  }

  /**
   * Setter for Player morale.
   *
   * @param morale value for Player morale.
   */
  public void updateMorale(int morale) {
    this.morale += morale;
    this.morale = this.morale < MIN_MORALE ? MIN_MORALE : Math.min(this.morale, MAX_MORALE);
  }

  // business methods

  public String overnightStatusUpdate() {
    String result;
    SuccessRate successRate;
    double overnightPreparedness = shelter.getIntegrity();
    if(shelter.hasFire()) {
      overnightPreparedness += 10;
    }
    if(overnightPreparedness < 10) {
      successRate = SuccessRate.HIGH;
      result = "It was a long cold night. I have to light a fire tonight!";
      updateMorale(-3);
    } else if(overnightPreparedness < 17) {
      successRate = SuccessRate.MEDIUM;
      result = "It was sure nice to have a fire last night, but this shelter doesn't provide much protection from the elements.";
      updateMorale(1);
    } else {
      successRate = SuccessRate.LOW;
      result = "Last night was great! I feel refreshed and ready to take on whatever comes my way today.";
      updateMorale(2);
    }
    double caloriesBurned = ActivityLevel.MEDIUM.getCaloriesBurned(successRate);
    updateWeight(-caloriesBurned);
    int hydrationCost = ActivityLevel.MEDIUM.getHydrationCost(successRate);
    setHydration(this.getHydration() - hydrationCost);
    return result;
  }

  // helper methods

  /**
   * Helper method for updating Player weight depending on the calories produced or expended
   * during a Player activity.
   *
   * @param calories Calories produced or expended during a Player activity.
   */
  public void updateWeight(double calories) {
    this.weight += round(calories / CALORIES_PER_POUND, 1);
  }

  /**
   * Helper method for rounding double values. Thank you to https://www.baeldung.com/java-round-decimal-number
   * @param value Value to be rounded.
   * @param places Desired decimal places.
   * @return Rounded value.
   */
  public static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    BigDecimal bd = new BigDecimal(Double.toString(value));
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }

  /**
   * Helper method for determining if Result of player activity gets amplified.
   *
   * @param boosterItems Items that could boost activity Result if Player possesses them.
   * @return Factor by which Player activity Result gets boosted.
   */
  public double getActivityBoostFactor(Item[] boosterItems) {
    double boostValue = 0.0;
    for (Item item : boosterItems) {
      if (this.items.contains(item)) {
        boostValue += 0.1;
      }
    }
    return boostValue;
  }

  /**
   * Helper method for generating a random SuccessRate.
   * @return Random SuccessRate.
   */
  public static SuccessRate generateSuccessRate() {
    int seed = ((int) Math.floor(Math.random() * 3));
    if (seed == 0) {
      return SuccessRate.LOW;
    } else if (seed == 1) {
      return SuccessRate.MEDIUM;
    } else {
      return SuccessRate.HIGH;
    }
  }

  /**
   * Player toString override.
   * @return String representation of the Player.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("\nCURRENT STATUS\n")
        .append("hydration: ").append(hydration)
        .append(", weight: ").append(weight)
        .append(", morale: ").append(morale)
        .append("\nItems: ");
    for(Item item : items) {
      sb.append(item).append(", ");
    }
    sb.delete(sb.length() - 2, sb.length() - 1);
    sb.append("\nShelter - ")
        .append("integrity: ").append(this.getShelter().getIntegrity())
        .append(", firewood: ").append(this.getShelter().getFirewood())
        .append(", water: ").append(this.getShelter().getWaterTank())
        .append("\nFood Cache");
    for(Food food : this.getShelter().getFoodCache().keySet()) {
      sb.append("\n  ").append(food).append(": ");
      double foodWeightInGrams = round(this.getShelter().getFoodCache().get(food), 1);
      // if food weight greater than a pound, display in pounds
      if(foodWeightInGrams > 456) {
        double foodWeightInPounds = round(foodWeightInGrams / 436, 1);
        sb.append(foodWeightInPounds).append(" pounds");
      }
      // if food weight greater than an ounce, display in ounces
      else if(foodWeightInGrams > 28) {
        double foodWeightInOunces = round(foodWeightInGrams / 28, 1);
        sb.append(foodWeightInOunces).append(" ounces");
      }
      // else display in grams
      else {
        sb.append(foodWeightInGrams).append(" grams");
      }
    }
    sb.append("\nItems in Shelter");
    for(Item item : this.getShelter().getEquipment().keySet()) {
      int itemCount = this.getShelter().getEquipment().get(item);
      sb.append("\n  ").append(item).append(": ").append(itemCount);
    }
    return sb.toString();
  }
}
