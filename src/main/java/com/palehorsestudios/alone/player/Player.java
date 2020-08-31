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
  private static final int MAX_ITEM_CARRY_SIZE = 3;
  private final Set<Item> items;
  private final Shelter shelter;
  // instance vars
  private int hydration;
  private double weight;
  private int morale;

  /** Public constructor for Player. */
  public Player(Set<Item> items) {
    this.hydration = 5;
    this.weight = 180;
    this.morale = 5;
    this.items = new HashSet<>();
    this.shelter = new Shelter();
    for (Item item : items) {
      this.shelter.addEquipment(item, 1);
    }
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
   * Method for transferring item from the shelter to the player. Player can only carry {@value
   * Player#MAX_ITEM_CARRY_SIZE} items.
   *
   * @param item Item to be transferred from the shelter to the player.
   * @return Result of the transfer.
   */
  public Result getItemFromShelter(Item item) {
    // determine if player has less than the maximum carry limit
    if (this.items.size() < MAX_ITEM_CARRY_SIZE) {
      Result removalResult = this.shelter.removeEquipment(item, 1);
      this.items.add(removalResult.getItem());
      return removalResult;
    } else {
      Result.Builder resultBuilder = new Result.Builder();
      return resultBuilder
          .item(item)
          .itemCount(0)
          .message("You can only carry " + MAX_ITEM_CARRY_SIZE + " items.")
          .build();
    }
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
   * Activity method for the player to drink water.
   *
   * @return Result of drinking water.
   */
  public Result drinkWater() {
    Result.Builder resultBuilder = new Result.Builder();
    if (this.shelter.getWaterTank() > 0) {
      this.shelter.updateWater(-1);
      this.setHydration(this.getHydration() + 1);
      resultBuilder.message(
          "That's better. Your hydration is now at "
              + this.getHydration()
              + ", and you have "
              + this.shelter.getWaterTank()
              + " water(s) remaining.");
    } else {
      resultBuilder.message(
          "There isn't a drop left in your water tank. You should go fetch some water soon before you die of thirst!");
    }
    return resultBuilder.hydration(this.getHydration()).build();
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
    // get boost factor based on items the player is carrying
    double boostFactor =
        getActivityBoostFactor(
            new Item[] {
              Item.SURVIVAL_MANUAL, Item.FISHING_HOOKS, Item.FISHING_LINE, Item.FISHING_LURES
            });
    resultBuilder.calories(-caloriesBurned).food(Food.FISH);
    // gear, maybe we should eliminate low success rate possibility.
    if (successRate == SuccessRate.LOW) {
      updateMorale(-2);
      resultBuilder
          .foodCount(0)
          .message("I guess that's why they don't call it catching. You didn't catch any fish.");
    } else if (successRate == SuccessRate.MEDIUM) {
      this.getShelter()
          .addFoodToCache(Food.FISH, Food.FISH.getGrams() + Food.FISH.getGrams() * boostFactor);
      updateMorale(2);
      resultBuilder
          .foodCount(Food.FISH.getGrams() + Food.FISH.getGrams() * boostFactor)
          .message("It looks like you'll be eating fresh fish tonight! You caught one lake trout.");
    } else {
      this.getShelter()
          .addFoodToCache(
              Food.FISH, Food.FISH.getGrams() * 3 + Food.FISH.getGrams() * 3 * boostFactor);
      updateMorale(3);
      resultBuilder
          .foodCount(Food.FISH.getGrams() * 3 + Food.FISH.getGrams() * 3 * boostFactor)
          .message("I hope there's room in your food cache. You caught three white fish!");
    }
    return resultBuilder.morale(this.getMorale()).build();
  }

  /**
   * Activity method for Player to go hunting.
   * @return Result of the hunting trip.
   */
  public Result goHunting() {
    Result.Builder resultBuilder = new Result.Builder();
    SuccessRate successRate = generateSuccessRate();
    double caloriesBurned = ActivityLevel.HIGH.getCaloriesBurned(successRate);
    updateWeight(-caloriesBurned);
    // get boost factor based on items the player is carrying
    double boostFactor =
        getActivityBoostFactor(
            new Item[] {
              Item.SURVIVAL_MANUAL,
              Item.ARROWS,
              Item.BOW,
              Item.PISTOL,
              Item.PISTOL_CARTRIDGES,
              Item.KNIFE
            });
    resultBuilder.calories(-caloriesBurned);
    // gear, maybe we should eliminate low success rate possibility.
    if (successRate == SuccessRate.LOW) {
      updateMorale(-2);
      resultBuilder
          .foodCount(0)
          .message(
              "I guess that's why they don't call it killing. You couldn't get a shot on an animal.");
    } else if (successRate == SuccessRate.MEDIUM) {
      this.getShelter()
          .addFoodToCache(
              Food.PORCUPINE, Food.PORCUPINE.getGrams() + Food.PORCUPINE.getGrams() * boostFactor);
      updateMorale(2);
      resultBuilder
          .foodCount(Food.PORCUPINE.getGrams() + Food.PORCUPINE.getGrams() * boostFactor)
          .message(
              "Watch out for those quills! You killed a nice fat porcupine that should keep you fed for a while.");
    } else {
      this.getShelter()
          .addFoodToCache(Food.MOOSE, Food.MOOSE.getGrams() + Food.MOOSE.getGrams() * boostFactor);
      updateMorale(4);
      resultBuilder
          .foodCount(Food.MOOSE.getGrams() + Food.MOOSE.getGrams() * boostFactor)
          .message(
              "Moose down! It took five trips, but you were able to process the meat and transport it back to your shelter before a predator got to it first.");
    }
    return resultBuilder.morale(this.getMorale()).build();
  }

  /**
   * Activity method for Player to trap animals.
   *
   * @return Result of trapping attempt.
   */
  public Result goTrapping() {
    Result.Builder resultBuilder = new Result.Builder();
    SuccessRate successRate = generateSuccessRate();
    double caloriesBurned = ActivityLevel.MEDIUM.getCaloriesBurned(successRate);
    updateWeight(-caloriesBurned);
    resultBuilder.calories(-caloriesBurned);
    double boostFactor =
        getActivityBoostFactor(new Item[] {Item.SURVIVAL_MANUAL, Item.WIRE, Item.KNIFE});
    // gear, maybe we should eliminate low success rate possibility.
    if (successRate == SuccessRate.LOW) {
      updateMorale(-2);
      resultBuilder
          .foodCount(0)
          .message("Those varmints are smarter than they look. Your traps were empty.");
    } else if (successRate == SuccessRate.MEDIUM) {
      this.getShelter()
          .addFoodToCache(
              Food.SQUIRREL,
              Food.SQUIRREL.getGrams() * 2 + Food.SQUIRREL.getGrams() * 2 * boostFactor);
      updateMorale(1);
      resultBuilder
          .food(Food.SQUIRREL)
          .foodCount(Food.SQUIRREL.getGrams() * 2 + Food.SQUIRREL.getGrams() * 2 * boostFactor)
          .message("Your patience has paid off. There were two squirrels in your traps!");
    } else {
      this.getShelter()
          .addFoodToCache(
              Food.RABBIT, Food.RABBIT.getGrams() * 3 + Food.RABBIT.getGrams() * 3 * boostFactor);
      updateMorale(2);
      resultBuilder
          .food(Food.RABBIT)
          .foodCount(Food.RABBIT.getGrams() * 3 + Food.RABBIT.getGrams() * 3 * boostFactor)
          .message("You'll have plenty of lucky rabbit feet now. Your snared three rabbits!");
    }
    return resultBuilder.morale(this.getMorale()).build();
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

  /**
   * Private helper method for updating Player weight depending on the calories produced or expended
   * during a Player activity.
   *
   * @param calories Calories produced or expended during a Player activity.
   */
  private void updateWeight(double calories) {
    this.weight += ((calories / CALORIES_PER_POUND) * 10) / 10.0;
  }

  /**
   * Private helper method determining if Result of player activity gets amplified.
   *
   * @param boosterItems Items that could boost activity Result if Player possesses them.
   * @return Factor by which Player activity Result gets boosted.
   */
  private double getActivityBoostFactor(Item[] boosterItems) {
    double boostValue = 0.0;
    for (Item item : boosterItems) {
      if (this.items.contains(item)) {
        boostValue += 0.1;
      }
    }
    return boostValue;
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
