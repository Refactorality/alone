package com.palehorsestudios.alone;

import com.google.common.collect.ImmutableMap;
import com.palehorsestudios.alone.player.IllegalHydrationArgumentException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Shelter {
    private final Map<Food, Double> foodCache;
    private final Map<Item, Integer> equipment;
    private int integrity;
    private int firewood;
    private int waterTank;
    private final int MAX_WATER = 10;
    private final int MIN_WATER = 0;

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

    public int getWaterTank() {
        return waterTank;
    }

    public Result updateWater(int water) {
        Result.Builder resultBuilder = new Result.Builder();
        int currentWater = getWaterTank();
        waterTank += water;
        int addedWater;
        addedWater = water;
        if (waterTank >= MAX_WATER) {
            this.waterTank = MAX_WATER;
            addedWater = MAX_WATER - currentWater;
        }
        else if (waterTank < MIN_WATER) {
            this.waterTank = MIN_WATER;
            throw new IllegalWaterUpdateException( "You can't get any water because there is none in water tank.")

        }
        return resultBuilder
                .water(addedWater)
                .build();
    }

    public ImmutableMap<Food, Double> getFoodCache() {
        return ImmutableMap.copyOf(foodCache);
    }

    public void setFirewood(int firewood) {
        this.firewood = firewood;
    }

  public Result addFoodToCache(Food food, double quantity) {
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

    public Result removeFoodFromCache(Food food, double quantity) {
        Result.Builder resultBuilder = new Result.Builder();
        Optional<Double> currentQuantity = Optional.ofNullable(this.foodCache.get(food));
        resultBuilder.food(food);
        if (currentQuantity.isPresent()) {
            if (currentQuantity.get() < quantity) {
                this.foodCache.put(food, 0.0);
                resultBuilder
                        .foodCount(currentQuantity.get())
                        .message("You were only able to find " + currentQuantity.get() + " grams of " + food + " in your food cache.");
            } else {
                this.foodCache.put(food, currentQuantity.get() - quantity);
                resultBuilder
                        .foodCount(quantity)
                        .message("You removed " + quantity + " grams of " + food + " from your food cache.");
            }
        } else {
            resultBuilder
                    .foodCount(0.0)
                    .message("You do not have any " + food + " in your food cache.");
        }
        return resultBuilder.build();
    }

    public ImmutableMap<Item, Integer> getEquipment() {
        return ImmutableMap.copyOf(equipment);
    }

    public Result addEquipment(Item item, int quantity) {
        Result.Builder resultBuilder = new Result.Builder();
        Optional<Integer> currentQuantity = Optional.ofNullable(this.equipment.get(item));
        if (currentQuantity.isPresent()) {
            this.equipment.put(item, currentQuantity.get() + quantity);
        } else {
            this.equipment.put(item, quantity);
        }
        return resultBuilder
                .item(item)
                .itemCount(quantity)
                .message(quantity + " " + item + " added to shelter")
                .build();
    }


  public Result removeEquipment(Item item, int quantity) {
    Result.Builder resultBuilder = new Result.Builder();
    Optional<Integer> currentQuantity = Optional.ofNullable(this.equipment.get(item));
    resultBuilder.item(item);
    if(currentQuantity.isPresent()) {
      if (currentQuantity.get() < quantity) {
        this.equipment.put(item, 0);
        resultBuilder
                .itemCount(currentQuantity.get())
                .message("You only had " + currentQuantity.get() + " " + item + " in your shelter.");
      } else {
        this.equipment.put(item, currentQuantity.get() - quantity);
        resultBuilder
                .itemCount(quantity)
                .message(quantity + " " + item + " removed from your shelter. You have " + (currentQuantity.get() - quantity) + " remaining.");
      }
    } else {
      resultBuilder
              .itemCount(0)
              .message("You do not have a(n) " + item + " in your shelter.");

    }
   return resultBuilder.build();
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