package com.palehorsestudios.alone.player;

import com.google.common.collect.ImmutableSet;
import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.Result;
import com.palehorsestudios.alone.Shelter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
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
    // boost shelter if Player has fire starting items
    if(items.contains(Item.FLINT_AND_STEEL) || items.contains(Item.MATCHES) || items.contains(Item.LIGHTER)) {
      this.shelter.setIntegrity(this.shelter.getIntegrity() + 1);
    }
    // boost shelter if Player has a tarp
    if(items.contains(Item.TARP)) {
      this.shelter.setIntegrity(this.shelter.getIntegrity() + 1);
    }
    // boost shelter if Player has items to assist in shelter construction
    if(items.contains(Item.PARACHUTE_CHORD) || items.contains(Item.AXE) || items.contains(Item.HATCHET) || items.contains(Item.SHOVEL) || items.contains(Item.KNIFE)) {
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
   *
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
              "Moose down! It took five trips, but you were able to process the meat and transport it back to " +
                  "your shelter before a predator got to it first.");
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

  /**
   * Activity method for Player to go foraging.
   *
   * @return Result of foraging attempt.
   */
  public Result goForaging() {
    Result.Builder resultBuilder = new Result.Builder();
    SuccessRate successRate = generateSuccessRate();
    double caloriesBurned = ActivityLevel.LOW.getCaloriesBurned(successRate);
    updateWeight(-caloriesBurned);
    resultBuilder.calories(-caloriesBurned);
    double boostFactor =
        getActivityBoostFactor(
            new Item[] {Item.SURVIVAL_MANUAL, Item.EXTRA_BOOTS, Item.KNIFE, Item.POT});
    // gear, maybe we should eliminate low success rate possibility.
    if (successRate == SuccessRate.LOW) {
      this.getShelter()
          .addFoodToCache(
              Food.BERRIES,
              Food.BERRIES.getGrams() * 2 + Food.BERRIES.getGrams() * 2 * boostFactor);
      updateMorale(1);
      resultBuilder
          .food(Food.BERRIES)
          .foodCount(Food.BERRIES.getGrams() * 2 + Food.BERRIES.getGrams() * 2 * boostFactor)
          .message(
              "Lucky for you, berries are ripe this time of year. You picked as many as you could carry.");
    } else if (successRate == SuccessRate.MEDIUM) {
      this.getShelter()
          .addFoodToCache(
              Food.MUSHROOM,
              Food.MUSHROOM.getGrams() * 4 + Food.MUSHROOM.getGrams() * 4 * boostFactor);
      updateMorale(1);
      resultBuilder
          .food(Food.MUSHROOM)
          .foodCount(Food.MUSHROOM.getGrams() * 4 + Food.MUSHROOM.getGrams() * 4 * boostFactor)
          .message("Delicious fungus! You found a log covered in edible mushrooms.");
    } else {
      this.getShelter()
          .addFoodToCache(
              Food.BUG, Food.BUG.getGrams() * 3 + Food.BUG.getGrams() * 3 * boostFactor);
      updateMorale(2);
      resultBuilder
          .food(Food.BUG)
          .foodCount(Food.BUG.getGrams() * 3 + Food.BUG.getGrams() * 3 * boostFactor)
          .message(
              "You never thought you would say this, but you are thrilled to have found a large group "
                  + "of leaf beetles under a decayed log. These critters are packed full of protein!");
    }
    return resultBuilder.morale(this.getMorale()).build();
  }

  /**
   * Activity method to allow Player to improve their shelter.
   *
   * @return Result of shelter improvement attempt.
   */
  public Result improveShelter() {
    Result.Builder resultBuilder = new Result.Builder();
    SuccessRate successRate = generateSuccessRate();
    double caloriesBurned = ActivityLevel.HIGH.getCaloriesBurned(successRate);
    updateWeight(-caloriesBurned);
    double boostFactor = getActivityBoostFactor(new Item[]{Item.KNIFE, Item.PARACHUTE_CHORD, Item.AXE, Item.HATCHET, Item.SHOVEL, Item.SURVIVAL_MANUAL});
    double improvementAmount;
    if (successRate == SuccessRate.LOW) {
      improvementAmount = 1 + 1 * boostFactor;
      resultBuilder.message(
          "You can sleep a little better at night. You were able to better insulate the walls of your shelter.");
    } else if (successRate == SuccessRate.MEDIUM) {
      improvementAmount = 2 + 2 * boostFactor;
      resultBuilder.message(
          "It's always nice to be able to get out of the rain and snow. Your roof is in better shape now.");
    } else {
      resultBuilder.message(
          "It was a lot of work, but your improved fireplace will keep you much warmer tonight");
      improvementAmount = 3 + 3 * boostFactor;
    }
    this.getShelter().setIntegrity(this.getShelter().getIntegrity() + improvementAmount);
    updateMorale((int) Math.ceil(improvementAmount / 2));
    return resultBuilder
        .shelterIntegrity(this.getShelter().getIntegrity())
        .calories(-caloriesBurned)
        .morale(this.getMorale())
        .build();
  }
  /**
   * Activity method for Player to gather firewood.
   *
   * @return Result of gathering firewood.
   */
  public Result gatherFirewood() {
    Result.Builder resultBuilder = new Result.Builder();
    SuccessRate successRate = generateSuccessRate();
    double caloriesBurned = ActivityLevel.MEDIUM.getCaloriesBurned(successRate);
    double firewoodAmount = 0.0;
    double boostFactor =
        getActivityBoostFactor(new Item[] {Item.WIRE, Item.AXE, Item.HATCHET});
    if (successRate == SuccessRate.LOW) {
      firewoodAmount = FIREWOOD_BUNDLE * 1.0 * (1.0 + boostFactor);
    } else if (successRate == SuccessRate.MEDIUM) {
      firewoodAmount = FIREWOOD_BUNDLE * 3.0 * (1.0 + boostFactor);
    } else if (successRate == SuccessRate.HIGH) {
      firewoodAmount = FIREWOOD_BUNDLE * 5.0 * (1.0 + boostFactor);
    }
    updateWeight(-caloriesBurned);
    updateMorale((int) Math.ceil(firewoodAmount / 2.0));
    return resultBuilder
        .firewood(firewoodAmount)
        .calories(-caloriesBurned)
        .message("Good Job! You just gathered " + firewoodAmount + " bundles of firewood.")
        .morale(this.getMorale())
        .build();
  }
  /**
   * Activity method for Player to get water.
   *
   * @return Result of getting water attempt.
   */
  public Result getWater() {
    Result.Builder resultBuilder = new Result.Builder();
    SuccessRate successRate = generateSuccessRate();
    double caloriesBurned = ActivityLevel.LOW.getCaloriesBurned(successRate);
    double boostFactor =
        getActivityBoostFactor(new Item[] {Item.IODINE_TABLETS});
    resultBuilder.calories(-caloriesBurned);
    updateWeight(-caloriesBurned);
    int addedWater;
    int finalAddedWater = 0;
    if (successRate == SuccessRate.LOW) {
      addedWater = 1 + (int)boostFactor*10;
      updateMorale(1);
    }
    else if (successRate == SuccessRate.MEDIUM) {
      addedWater = 2 + (int)boostFactor*10;
      updateMorale(1);
    }
    else {
      addedWater = 5 + (int)boostFactor*10;
      updateMorale(3);
    }
    finalAddedWater = this.shelter.updateWater(addedWater);
    resultBuilder
        .water(finalAddedWater)
        .morale(this.getMorale())
        .message("You added " + finalAddedWater + " in the water tank.");
    return resultBuilder.build();
  }
  /**
   * Activity method for Player to boost morale.
   *
   * @return Result of boosting morale attempt.
   */
  public Result boostMorale() {
    Result.Builder resultBuilder = new Result.Builder();
    Set<Item> moraleBoostItems = new HashSet<>();
    List<Item> moraleBoostItemsOwn = new ArrayList<>();
    moraleBoostItems.addAll(Arrays.asList(new Item[] {Item.FAMILY_PHOTO, Item.HARMONICA, Item.JOURNAL}));
    for (Item i : moraleBoostItems ) {
      if (this.items.contains(i)) {
        moraleBoostItemsOwn.add(i);
      }
    }
    if (moraleBoostItemsOwn.isEmpty()) {
      updateMorale(-1);
      return resultBuilder
          .message("It is cold and sad here. I know you are lonely, do you want to take some rest?")
          .morale(this.getMorale())
          .build();
    }
    // randomly pick a item from the moralBoostItemsOwn
    Random rand = new Random();
    int randomIndex = rand.nextInt(moraleBoostItemsOwn.size());
    if (moraleBoostItemsOwn.get(randomIndex) == Item.FAMILY_PHOTO ) {
      updateMorale(3);
      resultBuilder
          .message("You found your family photo and it reminds you all the good memories with your family! Your" +
              " morale is high now!");
    }
    else if (moraleBoostItemsOwn.get(randomIndex) == Item.HARMONICA ) {
      double caloriesBurned = ActivityLevel.LOW.getCaloriesBurned(SuccessRate.LOW);
      updateWeight(-caloriesBurned);
      updateMorale(2);
      resultBuilder
          .message("You found a harmonica, and you played with it for an hour, your morale is high now!");
    }
    else {
      double caloriesBurned = ActivityLevel.LOW.getCaloriesBurned(SuccessRate.LOW);
      updateWeight(-caloriesBurned);
      updateMorale(1);
      resultBuilder
          .message(("You found a Journal and a pen, you decide to capture current experience in the journal. " +
              "Your morale is high now!"));
    }
    resultBuilder.morale(this.getMorale());
    return resultBuilder.build();
  }
  /**
   * Activity method for Player to have some rest.
   *
   * @return Result of having rest.
   */
  public Result rest() {
    Result.Builder resultBuilder = new Result.Builder();
    // randomly generate the hours for rest
    Random rand = new Random();
    int hours = rand.nextInt(8);
    double boostFactor =
        getActivityBoostFactor(new Item[] {Item.FIRST_AID_KIT});
    updateMorale((int)boostFactor*10);
    // calories burning rate
    SuccessRate burnRate;
    if (hours < 4) {
      burnRate = SuccessRate.LOW;
    } else {
      burnRate = SuccessRate.MEDIUM;
    }
    double caloriesBurned = ActivityLevel.LOW.getCaloriesBurned(burnRate);
    updateWeight(-caloriesBurned);
    return resultBuilder
        .morale(this.getMorale())
        .message("You have rested for some hours and are ready for the next day!")
        .build();
  }

  // private helper methods
  /**
   * Private helper method for updating Player weight depending on the calories produced or expended
   * during a Player activity.
   *
   * @param calories Calories produced or expended during a Player activity.
   */
  private void updateWeight(double calories) {
    this.weight += Math.round((calories / CALORIES_PER_POUND) * 10) / 10.0;
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
      double foodWeightInGrams = Math.round(this.getShelter().getFoodCache().get(food) * 10) / 10.0;
      // if food weight greater than a pound, display in pounds
      if(foodWeightInGrams > 456) {
        double foodWeightInPounds = Math.round(foodWeightInGrams / 436 * 10) / 10.0;
        sb.append(foodWeightInPounds).append(" pounds");
      }
      // if food weight greater than an ounce, display in ounces
      else if(foodWeightInGrams > 28) {
        double foodWeightInOunces = Math.round(foodWeightInGrams / 28 * 10) / 10.0;
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
