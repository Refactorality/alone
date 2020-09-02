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
    shelter.addFoodToCache(Food.FISH, 1000);
    assertEquals(Optional.of(1000.0).get(), shelter.getFoodCache().get(Food.FISH));
  }

  @Test
  public void testRemoveFoodHappy() {
    shelter.addFoodToCache(Food.SQUIRREL, 1000.0);
    assertEquals(500.0, shelter.removeFoodFromCache(Food.SQUIRREL, 500.0), 0.001);
    assertEquals(500.0, shelter.getFoodCache().get(Food.SQUIRREL), 0.001);
  }

  @Test
  public void testRemoveFoodFail() {
    assertEquals(0.0, shelter.removeFoodFromCache(Food.RABBIT, 2000.0), 0.001);
  }

  @Test
  public void addEquipmentHappy() {
    shelter.addEquipment(Item.WIRE, 1);
    assertEquals(1, shelter.getEquipment().get(Item.WIRE), 0.0);
  }

  @Test
  public void removeEquipmentHappy() {
    shelter.addEquipment(Item.FISHING_HOOKS, 3);
    assertEquals(1, shelter.removeEquipment(Item.FISHING_HOOKS, 1));
    assertEquals(Optional.of(2).get(), shelter.getEquipment().get(Item.FISHING_HOOKS));
  }

  @Test
  public void removeEquipmentFail() {
    assertEquals(0, shelter.removeEquipment(Item.FAMILY_PHOTO, 500));
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