package com.palehorsestudios.alone;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

public class Shelter {
    private final Map<Food, Double> foodCache;
    private final Map<Item, Integer> equipment;
    private int integrity;
    private int firewood;

    private Shelter(Map<Food, Double> foodCache, Map<Item, Integer> equipment) {
        this.foodCache = foodCache;
        this.equipment = equipment;
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

    public Map<Food, Double> getFoodCache() {
        return ImmutableMap.copyOf(foodCache);
    }

    public Map<Item, Integer> getEquipment() {
        return ImmutableMap.copyOf(equipment);
    }

}
