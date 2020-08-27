package com.palehorsestudios.alone;

import java.util.List;
import java.util.Map;

public class Shelter {
    private Map<Food, Double> foodCache;
    private List<Map<Item, Integer>> equipment;
    private int integrity;
    private int firewood;

    private Shelter(int integrity) {
        this.integrity = integrity;
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
        return foodCache;
    }

    public void setFoodCache(Map<Food, Double> foodCache) {
        this.foodCache = foodCache;
    }

    public List<Map<Item, Integer>> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Map<Item, Integer>> equipment) {
        this.equipment = equipment;
    }
}
