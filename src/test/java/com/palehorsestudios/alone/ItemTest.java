package com.palehorsestudios.alone;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTest {

  @Test
  public void testToString() {
    assertEquals("hatchet", Item.HATCHET.toString());
    assertEquals("batteries", Item.BATTERIES.toString());
    assertEquals("pistol", Item.PISTOL.toString());
    assertEquals("fishing lures", Item.FISHING_LURES.toString());
  }
}