package com.palehorsestudios.alone.player;

import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.Shelter;
import com.palehorsestudios.alone.activity.ActivityLevel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
   * Method for transferring item from the shelter to the player. Player can only carry {@value
   * Player#MAX_ITEM_CARRY_SIZE} items.
   *
   * @param item Item to be transferred from the shelter to the player.
   * @return Result of the transfer.
   */
  public String getItemFromShelter(Item item) {
    String result;
    /* determine if player has less than the maximum carry limit
       and item is in shelter. */
    Optional<Integer> shelterItemCount = Optional.ofNullable(this.shelter.getEquipment().get(item));
    if (shelterItemCount.isPresent() && shelterItemCount.get() > 0) {
      if(this.items.size() < MAX_ITEM_CARRY_SIZE) {
        int retrievalResult = this.shelter.removeEquipment(item, 1);
        result = "You retrieved " + retrievalResult + " " + item + " from your shelter.";
        this.items.add(item);
      } else {
        result = "You can only carry " + MAX_ITEM_CARRY_SIZE + " items.";
      }
    } else {
      result = "You do not have a(n) " + item + " in your shelter.";
    }
    return result;
  }

  /**
   * Method for transferring item from the player to the shelter.
   *
   * @param item Item to be transferred from the player to the shelter.
   * @return Result of the transfer.
   */
  public String putItemInShelter(Item item) {
    String result;
    if (this.items.remove(item)) {
      this.shelter.addEquipment(item, 1);
      result = "One " + item + " moved to your shelter.";
    } else {
      result = "You do not have a(n) " + item + " on you.";
    }
    return result;
  }

  /**
   * Activity method for the player to eat food.
   *
   * @param food Food player trying to eat.
   * @return Result of player trying to eat food.
   */
  public String eat(Food food) {
    String result;
    double removalResult = this.shelter.removeFoodFromCache(food, SERVING_SIZE);
    if (removalResult > 0.0) {
      // update player weight
      // give a boost if they have a fire
      if(this.shelter.hasFire()) {
        updateWeight(food.getCaloriesPerGram() * removalResult * 1.1);
        result = "You had a nice warm meal of "  + food + " cooked over your fire.";
      } else {
        updateWeight(food.getCaloriesPerGram() * removalResult * 0.9);
        result = food + " doesn't taste very good uncooked. You should consider lighting a fire.";
      }
    } else {
      result = "You don't have any " + food + ".";
    }
    return result;
  }

  /**
   * Activity method for the player to drink water.
   *
   * @return Result of drinking water.
   */
  public String drinkWater() {
    String result;
    if (this.shelter.getWaterTank() > 0) {
      this.shelter.updateWater(-1);
      this.setHydration(this.getHydration() + 1);
      result = "That's better. Your hydration is now at "
              + this.getHydration()
              + ", and you have "
              + this.shelter.getWaterTank()
              + " water(s) remaining.";
    } else {
      result = "There isn't a drop left in your water tank. You should go fetch some water soon before you die of thirst!";
    }
    return result;
  }

  /**
   * Activity method for Player to go fishing.
   *
   * @return Result of fishing trip.
   */
  public String goFishing() {
    String result;
    SuccessRate successRate = generateSuccessRate();
    double caloriesBurned = ActivityLevel.MEDIUM.getCaloriesBurned(successRate);
    updateWeight(-caloriesBurned);
    int hydrationCost = ActivityLevel.MEDIUM.getHydrationCost(successRate);
    setHydration(this.getHydration() - hydrationCost);
    // get boost factor based on items the player is carrying
    double boostFactor =
        getActivityBoostFactor(
            new Item[] {
              Item.SURVIVAL_MANUAL, Item.FISHING_HOOKS, Item.FISHING_LINE, Item.FISHING_LURES
            });
    // gear, maybe we should eliminate low success rate possibility.
    if (successRate == SuccessRate.LOW) {
      updateMorale(-2);
      result = "I guess that's why they don't call it catching. You didn't catch any fish.";
    } else if (successRate == SuccessRate.MEDIUM) {
      this.getShelter()
          .addFoodToCache(Food.FISH, Food.FISH.getGrams() + Food.FISH.getGrams() * boostFactor);
      updateMorale(2);
      result = "It looks like you'll be eating fresh fish tonight! You caught one lake trout.";
    } else {
      this.getShelter()
          .addFoodToCache(
              Food.FISH, Food.FISH.getGrams() * 3 + Food.FISH.getGrams() * 3 * boostFactor);
      updateMorale(3);
      result = "I hope there's room in your food cache. You caught three white fish!";
    }
    return result;
  }

  /**
   * Activity method for Player to go hunting.
   *
   * @return Result of the hunting trip.
   */
  public String goHunting() {
    String result;
    SuccessRate successRate = generateSuccessRate();
    double caloriesBurned = ActivityLevel.HIGH.getCaloriesBurned(successRate);
    updateWeight(-caloriesBurned);
    int hydrationCost = ActivityLevel.HIGH.getHydrationCost(successRate);
    setHydration(this.getHydration() - hydrationCost);
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
    // gear, maybe we should eliminate low success rate possibility.
    if (successRate == SuccessRate.LOW) {
      updateMorale(-2);
      result = "I guess that's why they don't call it killing. You couldn't get a shot on an animal.";
    } else if (successRate == SuccessRate.MEDIUM) {
      this.getShelter()
          .addFoodToCache(
              Food.PORCUPINE, Food.PORCUPINE.getGrams() + Food.PORCUPINE.getGrams() * boostFactor);
      updateMorale(2);
      result = "Watch out for those quills! You killed a nice fat porcupine that should keep you fed for a while.";
    } else {
      this.getShelter()
          .addFoodToCache(Food.MOOSE, Food.MOOSE.getGrams() + Food.MOOSE.getGrams() * boostFactor);
      updateMorale(4);
      result = "Moose down! It took five trips, but you were able to process the meat and transport it back to " +
                  "your shelter before a predator got to it first.";
    }
    return result;
  }

  /**
   * Activity method for Player to trap animals.
   *
   * @return Result of trapping attempt.
   */
  public String goTrapping() {
    String result;
    SuccessRate successRate = generateSuccessRate();
    double caloriesBurned = ActivityLevel.MEDIUM.getCaloriesBurned(successRate);
    updateWeight(-caloriesBurned);
    int hydrationCost = ActivityLevel.MEDIUM.getHydrationCost(successRate);
    setHydration(this.getHydration() - hydrationCost);
    double boostFactor =
        getActivityBoostFactor(new Item[] {Item.SURVIVAL_MANUAL, Item.WIRE, Item.KNIFE});
    // gear, maybe we should eliminate low success rate possibility.
    if (successRate == SuccessRate.LOW) {
      updateMorale(-2);
      result = "Those varmints are smarter than they look. Your traps were empty.";
    } else if (successRate == SuccessRate.MEDIUM) {
      this.getShelter()
          .addFoodToCache(
              Food.SQUIRREL,
              Food.SQUIRREL.getGrams() * 2 + Food.SQUIRREL.getGrams() * 2 * boostFactor);
      updateMorale(1);
      result = "Your patience has paid off. There were two squirrels in your traps!";
    } else {
      this.getShelter()
          .addFoodToCache(
              Food.RABBIT, Food.RABBIT.getGrams() * 3 + Food.RABBIT.getGrams() * 3 * boostFactor);
      updateMorale(2);
      result = "You'll have plenty of lucky rabbit feet now. Your snared three rabbits!";
    }
    return result;
  }

  /**
   * Activity method for Player to go foraging.
   *
   * @return Result of foraging attempt.
   */
  public String goForaging() {
    String result;
    SuccessRate successRate = generateSuccessRate();
    double caloriesBurned = ActivityLevel.LOW.getCaloriesBurned(successRate);
    updateWeight(-caloriesBurned);
    int hydrationCost = ActivityLevel.LOW.getHydrationCost(successRate);
    setHydration(this.getHydration() - hydrationCost);
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
      result = "Lucky for you, berries are ripe this time of year. You picked as many as you could carry.";
    } else if (successRate == SuccessRate.MEDIUM) {
      this.getShelter()
          .addFoodToCache(
              Food.MUSHROOM,
              Food.MUSHROOM.getGrams() * 4 + Food.MUSHROOM.getGrams() * 4 * boostFactor);
      updateMorale(1);
      result = "Delicious fungus! You found a log covered in edible mushrooms.";
    } else {
      this.getShelter()
          .addFoodToCache(
              Food.BUG, Food.BUG.getGrams() * 3 + Food.BUG.getGrams() * 3 * boostFactor);
      updateMorale(2);
      result = "You never thought you would say this, but you are thrilled to have found a large group "
                  + "of leaf beetles under a decayed log. These critters are packed full of protein!";
    }
    return result;
  }

  /**
   * Activity method to allow Player to improve their shelter.
   *
   * @return Result of shelter improvement attempt.
   */
  public String improveShelter() {
    String result;
    SuccessRate successRate = generateSuccessRate();
    double caloriesBurned = ActivityLevel.HIGH.getCaloriesBurned(successRate);
    updateWeight(-caloriesBurned);
    int hydrationCost = ActivityLevel.HIGH.getHydrationCost(successRate);
    setHydration(this.getHydration() - hydrationCost);
    double boostFactor = getActivityBoostFactor(new Item[]{Item.KNIFE, Item.PARACHUTE_CHORD, Item.AXE, Item.HATCHET, Item.SHOVEL, Item.SURVIVAL_MANUAL});
    double improvementAmount;
    if (successRate == SuccessRate.LOW) {
      improvementAmount = 1 + 1 * boostFactor;
      result = "Slowly but surely, you continue to improve on some semblance of a shelter.";
    } else if (successRate == SuccessRate.MEDIUM) {
      improvementAmount = 2 + 2 * boostFactor;
      result = "You have a new idea on a way to improve your shelter. You're confident that it will be more comfortable now.";
    } else {
      result = "Your shelter is coming along nicely, with several improvements you were able to implement.";
      improvementAmount = 3 + 3 * boostFactor;
    }
    this.getShelter().setIntegrity(this.getShelter().getIntegrity() + improvementAmount);
    updateMorale((int) Math.ceil(improvementAmount / 2));
    return result;
  }

  /**
   * Activity method for Player to gather firewood.
   *
   * @return Result of gathering firewood.
   */
  public String gatherFirewood() {
    SuccessRate successRate = generateSuccessRate();
    double caloriesBurned = ActivityLevel.MEDIUM.getCaloriesBurned(successRate);
    updateWeight(-caloriesBurned);
    int hydrationCost = ActivityLevel.MEDIUM.getHydrationCost(successRate);
    setHydration(this.getHydration() - hydrationCost);
    double firewoodAmount = 0.0;
    double boostFactor =
        getActivityBoostFactor(new Item[] {Item.PARACHUTE_CHORD, Item.AXE, Item.HATCHET});
    if (successRate == SuccessRate.LOW) {
      firewoodAmount = FIREWOOD_BUNDLE * 1.0 * (1.0 + boostFactor);
    } else if (successRate == SuccessRate.MEDIUM) {
      firewoodAmount = FIREWOOD_BUNDLE * 3.0 * (1.0 + boostFactor);
    } else if (successRate == SuccessRate.HIGH) {
      firewoodAmount = FIREWOOD_BUNDLE * 5.0 * (1.0 + boostFactor);
    }
    firewoodAmount = round(firewoodAmount, 1);
    updateMorale((int) Math.ceil(firewoodAmount / 2.0));
    this.shelter.updateFirewood(firewoodAmount);
    return "Good Job! You just gathered " + firewoodAmount + " bundles of firewood.";
  }

  /**
   * Activity method for Player to build a fire.
   *
   * @return Result of building a fire.
   */
  public String buildFire() {
    String result;
    if(shelter.getFirewood() <= 0) {
      result = "You don't have any firewood.";
    } else {
      double boostFactor =
          getActivityBoostFactor(new Item[] {Item.SURVIVAL_MANUAL, Item.LIGHTER, Item.MATCHES, Item.FLINT_AND_STEEL});
      SuccessRate successRate;
      if(boostFactor == 0.0) {
        successRate = SuccessRate.LOW;
        updateWeight(-ActivityLevel.LOW.getCaloriesBurned(SuccessRate.HIGH));
        setHydration(hydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.HIGH));
      } else if(boostFactor == 0.1) {
        successRate = SuccessRate.MEDIUM;
        updateWeight(-ActivityLevel.LOW.getCaloriesBurned(SuccessRate.MEDIUM));
        setHydration(hydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.MEDIUM));
      } else if(boostFactor == 0.2) {
        successRate = SuccessRate.MEDIUM;
        updateWeight(-ActivityLevel.LOW.getCaloriesBurned(SuccessRate.MEDIUM));
        setHydration(hydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.MEDIUM));
      } else {
        successRate = SuccessRate.HIGH;
        updateWeight(-ActivityLevel.LOW.getCaloriesBurned(SuccessRate.LOW));
        setHydration(hydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.LOW));
      }
      int attemptOutcome;
      if (successRate == SuccessRate.LOW) {
        attemptOutcome = (int) Math.floor(Math.random() * 4);
      } else if (successRate == SuccessRate.MEDIUM) {
        attemptOutcome = (int) Math.floor(Math.random() * 2);
      } else {
        attemptOutcome = 1;
      }
      if(attemptOutcome == 1) {
        this.shelter.setFire(true);
        updateMorale(2);
        shelter.updateFirewood(-1);
        result = "It's amazing how much more bearable surviving is with a warm fire.";
      } else {
        this.shelter.setFire(false);
        updateMorale(-2);
        result = "That is depressing. You can't seem to get the fire started.";
      }
    }
    return result;
  }

  /**
   * Activity method for Player to get water.
   *
   * @return Result of getting water attempt.
   */
  public String getWater() {
    SuccessRate successRate = generateSuccessRate();
    double caloriesBurned = ActivityLevel.LOW.getCaloriesBurned(successRate);
    updateWeight(-caloriesBurned);
    int hydrationCost = ActivityLevel.LOW.getHydrationCost(successRate);
    setHydration(this.getHydration() - hydrationCost);
    double boostFactor =
        getActivityBoostFactor(new Item[] {Item.IODINE_TABLETS, Item.POT, Item.EXTRA_BOOTS});
    int addedWater;
    int finalAddedWater;
    if (successRate == SuccessRate.LOW) {
      addedWater = 3 + ((int) Math.ceil(boostFactor * 10));
      updateMorale(1);
    }
    else if (successRate == SuccessRate.MEDIUM) {
      addedWater = 4 + ((int) Math.ceil(boostFactor * 10));
      updateMorale(1);
    }
    else {
      addedWater = 5 + ((int) Math.ceil(boostFactor * 10));
      updateMorale(2);
    }
    finalAddedWater = this.shelter.updateWater(addedWater);
    return "You added " + finalAddedWater + " in the water tank.";
  }
  /**
   * Activity method for Player to boost morale.
   *
   * @return Result of boosting morale attempt.
   */
  public String boostMorale() {
    String result;
    double caloriesBurned = ActivityLevel.LOW.getCaloriesBurned(SuccessRate.LOW);
    updateWeight(-caloriesBurned);
    int hydrationCost = ActivityLevel.LOW.getHydrationCost(SuccessRate.LOW);
    setHydration(this.getHydration() - hydrationCost);
    List<Item> moraleBoostItemsOwn = new ArrayList<>();
    Set<Item> moraleBoostItems = new HashSet<>(Arrays.asList(Item.FAMILY_PHOTO, Item.HARMONICA, Item.JOURNAL));
    for (Item i : moraleBoostItems ) {
      if (this.items.contains(i)) {
        moraleBoostItemsOwn.add(i);
      }
    }
    if (moraleBoostItemsOwn.isEmpty()) {
      updateMorale(-1);
      result = "It is cold and sad here. You wish you had something to lift your spirits. Do you want to take some rest?";
    } else {
      // randomly pick a item from the moralBoostItemsOwn
      Random rand = new Random();
      int randomIndex = rand.nextInt(moraleBoostItemsOwn.size());
      if (moraleBoostItemsOwn.get(randomIndex) == Item.FAMILY_PHOTO ) {
        updateMorale(3);
        result = "You found your family photo, and it reminds you all the good memories with your family! Your" +
                " morale is high now!";
      }
      else if (moraleBoostItemsOwn.get(randomIndex) == Item.HARMONICA ) {
        updateMorale(2);
        result = "You played your harmonica for an hour. Your morale is high now!";
      }
      else {
        updateMorale(1);
        result = "You decide to capture your current experience in your journal. " +
                "You are feeling much better.";
      }
    }
    return result;
  }

  /**
   * Activity method for Player to have some rest.
   *
   * @return Result of having rest.
   */
  public String rest() {
    // randomly generate the hours for rest
    Random rand = new Random();
    int hours = rand.nextInt(8);
    // calories burning rate
    SuccessRate burnRate;
    if (hours < 4) {
      burnRate = SuccessRate.LOW;
      updateMorale(1);
    } else {
      burnRate = SuccessRate.MEDIUM;
      updateMorale(2);
    }
    double caloriesBurned = ActivityLevel.LOW.getCaloriesBurned(burnRate);
    updateWeight(-caloriesBurned);
    int hydrationCost = ActivityLevel.LOW.getHydrationCost(burnRate);
    setHydration(this.getHydration() - hydrationCost);
    return "You rested for " + hours + " hours and are ready for the next adventure!";
  }

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
