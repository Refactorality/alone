package com.palehorsestudios.alone;

import com.google.common.collect.ImmutableMap;
import com.palehorsestudios.alone.player.IllegalHydrationArgumentException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void setFirewood(int firewood) {
        this.firewood = firewood;
    }

    public int getWaterTank() { return waterTank; }

    public void addWater(int water) {
        waterTank += water;
        if (waterTank >= MAX_WATER) { this.waterTank = MAX_WATER; }
    }

    public void removeWater(int water) {
        waterTank -= water;
        if (waterTank < MIN_WATER) { this.waterTank = MIN_WATER; }
    }

    public ImmutableMap<Food, Double> getFoodCache() {
        return ImmutableMap.copyOf(foodCache);
    }

    public void addFood(Food food, double quantity) {
        double currentQuantity = this.foodCache.get(food);
        this.foodCache.put(food, currentQuantity + quantity);
    }

    public void removeFood(Food food, double quantity) {
        double currentQuantity = this.foodCache.get(food);
        this.foodCache.put(food, currentQuantity - quantity);
    }

    public ImmutableMap<Item, Integer> getEquipment() {
        return ImmutableMap.copyOf(equipment);
    }

    public void addEquipment(Item item, int quantity) {
        int currentQuantity = this.equipment.get(item);
        this.equipment.put(item, currentQuantity + quantity);
    }

    public void removeEquipment(Item item, int quantity) {
        int currentQuantity = this.equipment.get(item);
        this.equipment.put(item, currentQuantity - quantity);
    }
}
