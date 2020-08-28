package com.palehorsestudios.alone.player;

import com.google.common.collect.ImmutableSet;
import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.IllegalEquipmentRemovalException;
import com.palehorsestudios.alone.IllegalFoodRemovalException;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.Result;
import com.palehorsestudios.alone.Shelter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;

public class Player {
    // static constants
    private static final int MIN_HYDRATION = 0;
    private static final int MAX_HYDRATION = 10;
    private static final int MIN_WEIGHT = 0;
    private static final int MIN_MORALE = 0;
    private static final int MAX_MORALE = 10;
    private static final int SERVING_SIZE = 227;
    private static final int FIREWOOD_BUNDLE = 1;
    private static final double CALORIES_PER_POUND = 285.7;
    private static final Logger logger = LoggerFactory.getLogger("Player logger");

    // instance vars
    private int hydration;
    private double weight;
    private int morale;
    private final Set<Item> items;
    private final Shelter shelter;

    /**
     * Public constructor for Player.
     */
    public Player(Set<Item> items) {
        this.hydration = 10;
        this.weight = 180;
        this.morale = 5;
        this.items = new HashSet<>(items);
        this.shelter = new Shelter();
    }

    // getters

    /**
     * Getter for Player hydration.
     * @return Player hydration.
     */
    public int getHydration() {
        return hydration;
    }

    /**
     * Getter for Player weight.
     * @return Player weight.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Getter for Player morale.
     * @return Player morale.
     */
    public int getMorale() {
        return morale;
    }

    /**
     * Getter for items Player is currently carrying.
     * @return ImmutableSet of Player items.
     */
    public ImmutableSet<Item> getItems() {
        return ImmutableSet.copyOf(this.items);
    }

    /**
     * Getter for Player's shelter.
     * @return Player's shelter.
     */
    public Shelter getShelter() {
        return this.shelter;
    }

    // setters

    /**
     * Setter for Player hydration.
     * @param hydration value for Player hydration.
     * @throws IllegalHydrationArgumentException if {@value Player#MAX_HYDRATION} < hydration < {@value Player#MIN_HYDRATION}
     */
    public void setHydration(int hydration) throws IllegalHydrationArgumentException {
        if(hydration < MIN_HYDRATION || hydration > MAX_HYDRATION) {
            throw new IllegalHydrationArgumentException(
                    "hydration must be greater than " + MIN_HYDRATION + ", and less than " + MAX_HYDRATION
            );
        }
        this.hydration = hydration;
    }

    /**
     * Setter for Player weight.
     * @param weight value for Player weight.
     * @throws IllegalWeightArgumentException if weight < {@value Player#MIN_WEIGHT}
     */
    public void setWeight(double weight) throws IllegalWeightArgumentException {
        if(weight < MIN_WEIGHT) {
            throw new IllegalWeightArgumentException(
                    "weight must be greater than " + MIN_WEIGHT
            );
        }
        this.weight = weight;
    }

    /**
     * Setter for Player morale.
     * @param morale value for Player morale.
     * @throws IllegalMoraleArgumentException if {@value Player#MAX_MORALE} < morale < {@value Player#MIN_MORALE}
     */
    public void updateMorale(int morale) {
        this.morale += morale;
        if (morale < MIN_MORALE) {
            this.morale = MIN_MORALE;
            // Should we create a scenario that the player dies if morale is 0?
        } else if ( morale > MAX_MORALE) {
            this.morale = MAX_MORALE;
        }
    }

    public Result addItem(Item item) {
        Result.Builder resultBuilder = new Result.Builder();
        try {
            this.shelter.removeEquipment(item, 1);
        } catch (IllegalEquipmentRemovalException e) {
            resultBuilder.message(e.getMessage());
        }
        this.items.add(item);
        resultBuilder.message(item + " added");
        return resultBuilder.build();
    }

    public Result removeItem(Item item) {
        Result.Builder resultBuilder = new Result.Builder();
        this.items.remove(item);
        this.shelter.addEquipment(item, 1);
        return resultBuilder.build();
    }

    // business methods
    public Result eat(Food food) {
        Result.Builder resultBuilder = new Result.Builder();
        // try to draw on shelter food cache
        try {
            this.shelter.removeFood(food, SERVING_SIZE);

            // update player weight
            this.updateWeight(food.getCaloriesPerGram() * SERVING_SIZE);

            // load the result builder
            resultBuilder
                    .food(food)
                    .foodCount(-SERVING_SIZE)
                    .message("You had a hearty meal of " + SERVING_SIZE + " grams of " + food);
        } catch (IllegalFoodRemovalException e) {
            resultBuilder.message(e.getMessage());
        }

        return resultBuilder.build();
    }

    public Result goFishing() {
        return null;
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
        Random rand = new Random();
        ActivityLevel activityLevel = ActivityLevel.MEDIAN;
        SuccessRate successRate = SuccessRate.values()[rand.nextInt(SuccessRate.values().length)];
        double caloriesBurned = successRate.getRate()*activityLevel.getCaloriesBurnedPerHour();
        int firewoodAmount = successRate.getRate()*FIREWOOD_BUNDLE;
        int morale = successRate.getRate();
        updateWeight(-caloriesBurned);
        updateMorale(morale);
        return resultBuilder
                .firewood(firewoodAmount)
                .message("Good Job! You just gathered " + firewoodAmount + " buddles of firewood.")
                .morale(morale)
                .build();
    }

    public Result getWater() {
        Result.Builder resultBuilder = new Result.Builder();
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
        return "Player{" +
                "hydration=" + hydration +
                ", weight=" + weight +
                ", morale=" + morale +
                ", items=" + items +
                ", shelter=" + shelter +
                '}';
    }
}
