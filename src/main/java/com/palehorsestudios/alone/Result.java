package com.palehorsestudios.alone;

/** Class to be used throughout the app to return the result of various activities */
public class Result {
  private int hydration;
  private int water;
  private double foodCount;
  private Food food;
  private double shelterIntegrity;
  private int firewood;
  private int morale;
  private double calories;
  private Item item;
  private int itemCount;
  private String message;

  /**
   * Private Result constructor. Result constructed through the Builder.
   *
   * @param builder Result Builder.
   */
  private Result(Builder builder) {
    this.hydration = builder.hydration;
    this.water = builder.water;
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

  /**
   * Getter for Result hydration.
   *
   * @return Result hydration.
   */
  public int getHydration() {
    return hydration;
  }

  /**
   * Getter for Result water.
   *
   * @return Result water.
   */
  public int getWater() {
    return water;
  }

  /**
   * Getter for Result food count.
   *
   * @return Result food count.
   */
  public double getFoodCount() {
    return foodCount;
  }

  /**
   * Getter for Result food type.
   *
   * @return Result food type.
   */
  public Food getFood() {
    return food;
  }

  /**
   * Getter for Result shelter integrity.
   *
   * @return Result shelter integrity.
   */
  public double getShelterIntegrity() {
    return shelterIntegrity;
  }

  /**
   * Getter for Result firewood amount.
   *
   * @return Result firewood amount.
   */
  public int getFirewood() {
    return firewood;
  }

  /**
   * Getter for Result morale.
   *
   * @return Result morale.
   */
  public int getMorale() {
    return morale;
  }

  /**
   * Getter for Result calories.
   *
   * @return Result calories.
   */
  public double getCalories() {
    return calories;
  }

  /**
   * Getter for Result message.
   *
   * @return Result message.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Getter for Result item type.
   *
   * @return Result item type.
   */
  public Item getItem() {
    return item;
  }

  /**
   * Getter for Result item count.
   *
   * @return Result item count.
   */
  public int getItemCount() {
    return itemCount;
  }

  /**
   * toString override for Result class.
   *
   * @return Result message.
   */
  @Override
  public String toString() {
    return message;
  }

  /** Public static inner class for builder pattern. */
  public static class Builder {
    private int hydration;
    private int water;
    private double foodCount;
    private Food food;
    private double shelterIntegrity;
    private int firewood;
    private int morale;
    private double calories;
    private Item item;
    private int itemCount;
    private String message;

    /** Public builder constructor. */
    public Builder() {}

    /**
     * Builder method to initialize Result hydration.
     *
     * @param hydration Hydration result.
     * @return Result Builder.
     */
    public Builder hydration(int hydration) {
      this.hydration = hydration;
      return this;
    }

    /**
     * Builder method to initialize Result water.
     *
     * @param water Water result.
     * @return Result Builder.
     */
    public Builder water(int water) {
      this.water = water;
      return this;
    }

    /**
     * Builder method to initialize Result food count.
     *
     * @param foodCount Food count result.
     * @return Result Builder.
     */
    public Builder foodCount(double foodCount) {
      this.foodCount = foodCount;
      return this;
    }

    /**
     * Builder method to initialize Result food type.
     *
     * @param food Food result.
     * @return Result Builder.
     */
    public Builder food(Food food) {
      this.food = food;
      return this;
    }

    /**
     * Builder method to initialize Result shelter integrity.
     *
     * @param shelterIntegrity Shelter integrity result.
     * @return Result Builder.
     */
    public Builder shelterIntegrity(double shelterIntegrity) {
      this.shelterIntegrity = shelterIntegrity;
      return this;
    }

    /**
     * Builder method to initialize Result firewood.
     *
     * @param firewood Firewood result.
     * @return Result Builder.
     */
    public Builder firewood(int firewood) {
      this.firewood = firewood;
      return this;
    }

    /**
     * Builder method to initialize Result morale.
     *
     * @param morale Morale result.
     * @return Result Builder.
     */
    public Builder morale(int morale) {
      this.morale = morale;
      return this;
    }

    /**
     * Builder method to initialize Result calories.
     *
     * @param calories Calories result.
     * @return Result Builder.
     */
    public Builder calories(double calories) {
      this.calories = calories;
      return this;
    }

    /**
     * Builder method to initialize Result item.
     *
     * @param item Item result.
     * @return Result Builder.
     */
    public Builder item(Item item) {
      this.item = item;
      return this;
    }

    /**
     * Builder method to initialize Result item count.
     *
     * @param itemCount Item count result.
     * @return Result Builder.
     */
    public Builder itemCount(int itemCount) {
      this.itemCount = itemCount;
      return this;
    }

    /**
     * Builder method to initialize Result message.
     *
     * @param message Result message.
     * @return Result Builder.
     */
    public Builder message(String message) {
      this.message = message;
      return this;
    }

    /**
     * Builder method to construct new Result from Builder.
     *
     * @return New Result.
     */
    public Result build() {
      return new Result(this);
    }
  }
}
