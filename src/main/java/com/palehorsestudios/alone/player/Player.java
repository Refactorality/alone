package com.palehorsestudios.alone.player;

import com.google.common.collect.ImmutableSet;
import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.Result;
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
  private static final int FIREWOOD_BUNDLE = 1;
  private static final double CALORIES_PER_POUND = 285.7;
  private final Set<Item> items;
  private final Shelter shelter;
  // instance vars
  private int hydration;
  private double weight;
  private int morale;

  /** Public constructor for Player. */
  public Player(Set<Item> items) {
    this.hydration = 10;
    this.weight = 180;
    this.morale = 5;
    this.items = new HashSet<>(items);
    this.shelter = new Shelter();
  }

  // getters

  private static SuccessRate generateSuccessRate() {
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
  public ImmutableSet<Item> getItems() {
    return ImmutableSet.copyOf(this.items);
  }

  /**
   * Getter for Player's shelter.
   *
   * @return Player's shelter.
   */
  public Shelter getShelter() {
    return this.shelter;
  }

  // business methods

  /**
   * Setter for Player morale.
   *
   * @param morale value for Player morale.
   */
  public void updateMorale(int morale) {
    this.morale += morale;
    this.morale = this.morale < MIN_MORALE ? MIN_MORALE : Math.min(this.morale, MAX_MORALE);
  }

  /**
   * Method for transferring item from the shelter to the player.
   *
   * @param item Item to be transferred from the shelter to the player.
   * @return Result of the transfer.
   */
  public Result getItemFromShelter(Item item) {
    Result removalResult = this.shelter.removeEquipment(item, 1);
    if (removalResult.getItemCount() > 0) {
      this.items.add(item);
    }
    return removalResult;
  }

  /**
   * Method for transferring item from the player to the shelter.
   *
   * @param item Item to be transferred from the player to the shelter.
   * @return Result of the transfer.
   */
  public Result putItemInShelter(Item item) {
    Result.Builder resultBuilder = new Result.Builder();
    resultBuilder.item(item);
    if (this.items.remove(item)) {
      this.shelter.addEquipment(item, 1);
      resultBuilder.itemCount(1).message("One " + item + " moved to your shelter.");
    } else {
      resultBuilder.itemCount(0).message("You do not have a(n) " + item + " on you.");
    }
    return resultBuilder.build();
  }

  /**
   * Activity method for the player to eat food.
   *
   * @param food Food player trying to eat.
   * @return Result of player trying to eat food.
   */
  public Result eat(Food food) {
    Result.Builder resultBuilder = new Result.Builder();
    Result removalResult = this.shelter.removeFoodFromCache(food, SERVING_SIZE);
    resultBuilder.food(food);

    if (removalResult.getFoodCount() > 0.0) {
      // update player weight
      updateWeight(food.getCaloriesPerGram() * removalResult.getFoodCount());

      // load the result builder
      resultBuilder
          .food(food)
          .foodCount(removalResult.getFoodCount())
          .message(
              "You had a hearty meal of " + removalResult.getFoodCount() + " grams of " + food);
    } else {
      resultBuilder.foodCount(removalResult.getFoodCount()).message(removalResult.getMessage());
    }

    return resultBuilder.build();
  }

  /**
   * Activity method for Player to go fishing.
   *
   * @return Result of fishing trip.
   */
  public Result goFishing() {
    Result.Builder resultBuilder = new Result.Builder();
    SuccessRate successRate = generateSuccessRate();
    double caloriesBurned = ActivityLevel.MEDIUM.getCaloriesBurned(successRate);
    updateWeight(-caloriesBurned);
    resultBuilder.calories(-caloriesBurned).food(Food.FISH);
    if (successRate == SuccessRate.LOW) {
      updateMorale(-2);
      resultBuilder
          .foodCount(0)
          .message("I guess that's why they don't call it catching. You didn't catch any fish.");
    } else if (successRate == SuccessRate.MEDIUM) {
      this.getShelter().addFoodToCache(Food.FISH, Food.FISH.getGrams());
      updateMorale(2);
      resultBuilder
          .foodCount(Food.FISH.getGrams())
          .message("It looks like you'll be eating fresh fish tonight! You caught one lake trout");
    } else {
      this.getShelter().addFoodToCache(Food.FISH, Food.FISH.getGrams() * 3);
      updateMorale(3);
      resultBuilder
          .foodCount(Food.FISH.getGrams() * 3)
          .message("I hope there's room in your food cache. You caught three white fish!");
    }
    return resultBuilder.morale(this.getMorale()).build();
  }

  public Result goHunting() {
    return null;
  }

  public Result goTrapping() {
    return null;
  }

  public Result goForaging() {
    return null;
  }

  public Result improveShelter() {
    return null;
  }

  public Result gatherFirewood() {
    Result.Builder resultBuilder = new Result.Builder();
    SuccessRate successRate = generateSuccessRate();
    double caloriesBurned = ActivityLevel.MEDIUM.getCaloriesBurned(successRate);
    int firewoodAmount;
    if (successRate == SuccessRate.LOW) {
      firewoodAmount = FIREWOOD_BUNDLE;
    } else if (successRate == SuccessRate.MEDIUM) {
      firewoodAmount = FIREWOOD_BUNDLE * 3;
    } else {
      firewoodAmount = FIREWOOD_BUNDLE * 5;
    }
    updateWeight(-caloriesBurned);
    updateMorale((int) Math.ceil(firewoodAmount / 2.0));
    return resultBuilder
        .firewood(firewoodAmount)
        .calories(-caloriesBurned)
        .message("Good Job! You just gathered " + firewoodAmount + " bundles of firewood.")
        .morale(morale)
        .build();
  }

  public Result getWater() {
    return null;
  }

  public Result boostMorale() {
    return null;
  }

  public Result rest() {
    return null;
  }

  // private helper methods
  private void updateWeight(double calorie) {
    this.weight += ((calorie / CALORIES_PER_POUND) * 10) / 10.0;
  }

  @Override
  public String toString() {
    return "Player{"
        + "hydration="
        + hydration
        + ", weight="
        + weight
        + ", morale="
        + morale
        + ", items="
        + items
        + ", shelter="
        + shelter
        + '}';
  }
}
