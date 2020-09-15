package com.palehorsestudios.alone;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ItemTest {

  @Test
  public void testToString() {
    assertEquals("hatchet", GameAssets.gameItems.get("HATCHET").toString());
    assertEquals("batteries", GameAssets.gameItems.get("BATTERIES").toString());
    assertEquals("pistol", GameAssets.gameItems.get("PISTOL").toString());
    assertEquals("fishing lures", GameAssets.gameItems.get("FISHING_LURES").toString());
  }
}