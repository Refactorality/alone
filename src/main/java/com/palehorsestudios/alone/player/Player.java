package com.palehorsestudios.alone.player;

import com.google.common.collect.ImmutableSet;
import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.Shelter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class Player {
    // static constants
    static final int MIN_HYDRATION = 0;
    static final int MAX_HYDRATION = 10;
    static final int MIN_WEIGHT = 0;
    static final int MIN_MORALE = 0;
    static final int MAX_MORALE = 10;
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
        this.items = items;
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
    public Set<Item> getItems() {
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
    public void setMorale(int morale) throws IllegalMoraleArgumentException {
        if(morale < MIN_MORALE || morale > MAX_MORALE) {
            throw new IllegalMoraleArgumentException(
                    "morale must be greater than " + MIN_MORALE + ", and less than " + MAX_MORALE
            );
        }
        this.morale = morale;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void removeItem(Item item) {
        this.items.remove(item);
        this.shelter.getEquipment().
    }

    // business methods
    public Result eat(Food food, double amount) {
        Result.Builder resultBuilder = new Result.Builder();
        return resultBuilder
                .food(food)
                .foodCount(0 - amount)
                .message("You had a hearty meal of " + amount + " " + food)
                .build();
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
        return null;
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
    private void updateWeight(int calorie) {

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
