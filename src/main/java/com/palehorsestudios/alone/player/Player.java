package com.palehorsestudios.alone.player;

import com.google.common.base.Objects;
import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.HelperMethods;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.Shelter;
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

  /**
   * Helper method for updating Player weight depending on the calories produced or expended
   * during a Player activity.
   *
   * @param calories Calories produced or expended during a Player activity.
   */
  public void updateWeight(double calories) {
    this.weight += HelperMethods.round(calories / CALORIES_PER_POUND, 1);
  }

  public boolean isDead() {
    boolean gameOver = false;
    if (this.weight < 180.0 * 0.6) {
      gameOver = true;
    } else if (this.morale <= 0) {
      gameOver = true;
    } else if (this.hydration <= 0) {
      gameOver = true;
    }
    return gameOver;
  }

  public boolean isRescued(int days) {
    boolean playerIsRescued = false;
    if (days > 15) {
      playerIsRescued = ((int) Math.floor(Math.random() * 2)) != 0;
    }
    return playerIsRescued;
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
      double foodWeightInGrams = HelperMethods.round(this.getShelter().getFoodCache().get(food), 1);
      // if food weight greater than a pound, display in pounds
      if(foodWeightInGrams > 456) {
        double foodWeightInPounds = HelperMethods.round(foodWeightInGrams / 436, 1);
        sb.append(foodWeightInPounds).append(" pounds");
      }
      // if food weight greater than an ounce, display in ounces
      else if(foodWeightInGrams > 28) {
        double foodWeightInOunces = HelperMethods.round(foodWeightInGrams / 28, 1);
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Player player = (Player) o;
    return hydration == player.hydration &&
        Double.compare(player.weight, weight) == 0 &&
        morale == player.morale &&
        Objects.equal(items, player.items) &&
        Objects.equal(shelter, player.shelter);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(items, shelter, hydration, weight, morale);
  }
}
