package com.palehorsestudios.alone;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class ShelterTest {

  Shelter shelter;

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

  }

  @Test
  public void addEquipment() {}

  @Test
  public void removeEquipment() {}
}