package com.palehorsestudios.alone.dayencounter;

import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.activity.GetItemActivity;
import com.palehorsestudios.alone.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RescueHelicopterDayTest {

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
                Item.FLARE,
                Item.FIRST_AID_KIT,
                Item.COLD_WEATHER_GEAR));
    player = new Player(items);
  }

  @Test
  public void testEncounterNoFlare() {
    int previousMorale = player.getMorale();
    assertEquals("You hear a helicopter approaching your position rapidly from the north."
        + " You wave your arms and scream your lungs out, but it is no use."
        + " They continue flying south and pass your position half a mile to west."
        + " Perhaps if you had a flare they would have seen it.",
        RescueHelicopterDay.getInstance().encounter(player));
    assertEquals(previousMorale - 5, player.getMorale());
  }

  @Test
  public void testEncounterWithFlare() {
    GetItemActivity.getInstance().act(new Choice("get", player, Item.FLARE));
    assertEquals("You hear a helicopter approaching your position rapidly from the north."
        + " You ignite your flare and wave it wildly over your head."
        + " Incredibly, they spot your flare and land on a nearby beach."
        + " They greet you with a warm blanket and tell you to hop in."
        + " You are saved, but you will never forget this incredible experience.",
        RescueHelicopterDay.getInstance().encounter(player));
    assertTrue(player.isRescued());
  }
}