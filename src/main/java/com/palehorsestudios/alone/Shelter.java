package com.palehorsestudios.alone;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shelter {
    private final Map<Food, Double> foodCache;
    private final Map<Item, Integer> equipment;
    private int integrity;
    private int firewood;

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
