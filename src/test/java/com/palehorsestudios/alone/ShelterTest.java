package com.palehorsestudios.alone;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class ShelterTest {

  Shelter shelter;
  Logger logger = Logger.getLogger(ShelterTest.class.getName());

  @Before
  public void setUp() {
    shelter = new Shelter();
  }

  @Test
  public void testAddFoodToCacheHappy() {
    assertEquals("1000.0 grams of fish added to your food cache.", shelter.addFoodToCache(Food.FISH, 1000).getMessage());
    assertEquals(Optional.of(1000.0).get(), shelter.getFoodCache().get(Food.FISH));
  }

  @Test
  public void testRemoveFoodHappy() {
    shelter.addFoodToCache(Food.SQUIRREL, 1000.0);
    assertEquals("You removed 500.0 grams of squirrel from your food cache.", shelter.removeFoodFromCache(Food.SQUIRREL, 500.0).getMessage());
    assertEquals(500.0, shelter.getFoodCache().get(Food.SQUIRREL), 0.001);
  }

  @Test
  public void testRemoveFoodFail() {
    Result failedRemovalResult = shelter.removeFoodFromCache(Food.RABBIT, 2000.0);
    assertEquals("You do not have any rabbit in your food cache.", failedRemovalResult.getMessage());
    assertEquals(0.0, failedRemovalResult.getFoodCount(), 0.0001);
  }

  @Test
  public void addEquipmentHappy() {
    assertEquals("1 18 gauge wire added to shelter", shelter.addEquipment(Item.WIRE, 1).getMessage());
    assertEquals(1, shelter.getEquipment().get(Item.WIRE), 0.0);
  }

  @Test
  public void removeEquipmentHappy() {
    shelter.addEquipment(Item.FISHING_HOOKS, 3);
    assertEquals("1 fishing hooks removed from your shelter. You have 2 remaining.", shelter.removeEquipment(Item.FISHING_HOOKS, 1).getMessage());
    assertEquals(Optional.of(2).get(), shelter.getEquipment().get(Item.FISHING_HOOKS));
  }

  @Test
  public void removeEquipmentFail() {
    Result failedEquipmentRemovalResult = shelter.removeEquipment(Item.FAMILY_PHOTO, 500);
    assertEquals("You do not have a(n) family photo in your shelter.", failedEquipmentRemovalResult.getMessage());
    assertEquals(0, failedEquipmentRemovalResult.getItemCount());
  }

  @Test
  public void testUpdateWaterAddHappy() {
    shelter.updateWater(1);
    assertEquals(1, shelter.getWaterTank());
  }

  @Test
  public void testUpdateWaterOverfill() {
    shelter.updateWater(20);
    assertEquals(10, shelter.getWaterTank());
  }

  @Test
  public void testUpdateWaterRemoveHappy() {
    shelter.updateWater(5);
    shelter.updateWater(-2);
    assertEquals(3, shelter.getWaterTank());
  }

  @Test
  public void testUpdateWaterRemoveFail() {
    shelter.updateWater(-20);
    assertEquals(0, shelter.getWaterTank());

  }

}