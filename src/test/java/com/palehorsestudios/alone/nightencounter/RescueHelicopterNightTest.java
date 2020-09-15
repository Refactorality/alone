package com.palehorsestudios.alone.nightencounter;

import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RescueHelicopterNightTest {

  Player player;

  @Before
  public void setUp() throws Exception {
    Set<Item> items =
        new HashSet<>(
            Arrays.asList(
                Item.AXE,
                Item.KNIFE,
                Item.FISHING_LINE,
                Item.FISHING_HOOKS,
                Item.WIRE,
                Item.HARMONICA,
                Item.FLINT_AND_STEEL,
                Item.POT,
                Item.FIRST_AID_KIT,
                Item.COLD_WEATHER_GEAR));
    player = new Player(items);
  }

  @Test
  public void testEncounterWithoutFire() {
    player.getShelter().setFire(false);
    int previousMorale = player.getMorale();
    assertEquals("You wake from a deep slumber to the sound of an approaching helicopter."
        + " You wave your arms and scream your lungs out, but it is no use."
        + " They fly right over your camp. Perhaps if you had a fire they would have seen it.",
        RescueHelicopterNight.getInstance().encounter(player));
    assertEquals(previousMorale - 5, player.getMorale());
  }

  @Test
  public void testEncounterWithFire() {
    player.getShelter().setFire(true);
    assertEquals("You wake from a deep slumber to the sound of an approaching helicopter."
        + " Amazingly, they were ferrying people to a nearby island when they spotted your fire."
        + " They land on the beach, and greet you with a warm blanket and tell you to hop in."
        + " You are saved, but you will never forget this incredible experience.",
        RescueHelicopterNight.getInstance().encounter(player));
    assertTrue(player.isRescued());
  }
}