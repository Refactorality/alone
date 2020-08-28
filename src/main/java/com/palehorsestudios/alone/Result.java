package com.palehorsestudios.alone;

public class Result {
    private int hydration;
    private double foodCount;
    private Food food;
    private int shelterIntegrity;
    private int firewood;
    private int morale;
    private int calories;
    private Item item;
    private int itemCount;
    private String message;

    private Result(Builder builder) {
        this.hydration = builder.hydration;
        this.foodCount = builder.foodCount;
        this.food = builder.food;
        this.shelterIntegrity = builder.shelterIntegrity;
        this.firewood = builder.firewood;
        this.morale = builder.morale;
        this.calories = builder.calories;
        this.item = builder.item;
        this.itemCount = builder.itemCount;
        this.message = builder.message;
    }

    public static class Builder {
        private int hydration;
        private double foodCount;
        private Food food;
        private int shelterIntegrity;
        private int firewood;
        private int morale;
        private int calories;
        private Item item;
        private int itemCount;
        private String message;

        public Builder() {}

        public Builder hydration(int hydration) {
            this.hydration = hydration;
            return this;
        }

        public Builder foodCount(double foodCount) {
            this.foodCount = foodCount;
            return this;
        }

        public Builder food(Food food) {
            this.food = food;
            return this;
        }

        public Builder shelterIntegrity(int shelterIntegrity) {
            this.shelterIntegrity = shelterIntegrity;
            return this;
        }

        public Builder firewood(int firewood) {
            this.firewood = firewood;
            return this;
        }

        public Builder morale(int morale) {
            this.morale = morale;
            return this;
        }

        public Builder calories(int calories) {
            this.calories = calories;
            return this;
        }

        public Builder item(Item item) {
            this.item = item;
            return this;
        }

        public Builder itemCount(int itemCount) {
            this.itemCount = itemCount;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Result build() {
            return new Result(this);
        }
    }

    public int getHydration() {
        return hydration;
    }

    public double getFoodCount() {
        return foodCount;
    }

    public Food getFood() {
        return food;
    }

    public int getShelterIntegrity() {
        return shelterIntegrity;
    }

    public int getFirewood() {
        return firewood;
    }

    public int getMorale() {
        return morale;
    }

    public int getCalories() {
        return calories;
    }

    public String getMessage() {
        return message;
    }

    public Item getItem() {
        return item;
    }

    public int getItemCount() {
        return itemCount;
    }

    @Override
    public String toString() {
        return message;
    }
}
