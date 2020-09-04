package com.palehorsestudios.alone;

import com.palehorsestudios.alone.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class MainTest {

  Player player;

  @Before
  public void setUp() {
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
  public void testParseChoiceEat() {
    assertEquals(new Choice("eat", player), Main.parseChoice("eat", player));
    assertEquals(new Choice("eat", player, Food.MOOSE), Main.parseChoice("eat moose", player));
    assertEquals(new Choice("eat", player, Food.FISH), Main.parseChoice("eat fish", player));
    assertEquals(new Choice("eat", player, Food.SQUIRREL), Main.parseChoice("eat squirrel", player));
    assertEquals(new Choice("eat", player, Food.RABBIT), Main.parseChoice("eat rabbit", player));
    assertEquals(new Choice("eat", player, Food.PORCUPINE), Main.parseChoice("eat porcupine", player));
    assertEquals(new Choice("eat", player, Food.BUG), Main.parseChoice("eat bug", player));
    assertEquals(new Choice("eat", player, Food.BUG), Main.parseChoice("eat bugs", player));
    assertEquals(new Choice("eat", player, Food.MUSHROOM), Main.parseChoice("eat mushroom", player));
    assertEquals(new Choice("eat", player, Food.MUSHROOM), Main.parseChoice("eat mushrooms", player));
    assertEquals(new Choice("eat", player, Food.BERRIES), Main.parseChoice("eat berry", player));
    assertEquals(new Choice("eat", player, Food.BERRIES), Main.parseChoice("eat berries", player));
  }

  @Test
  public void testParseChoiceHunt() {
    assertEquals(new Choice("hunt", player), Main.parseChoice("hunt", player));
    assertEquals(new Choice("hunt", player), Main.parseChoice("hunt moose", player));
    assertEquals(new Choice("hunt", player), Main.parseChoice("hunt squirrel", player));
    assertEquals(new Choice("hunt", player), Main.parseChoice("hunt rabbit", player));
    assertEquals(new Choice("hunt", player), Main.parseChoice("hunt porcupine", player));
    assertEquals(new Choice("hunt", player), Main.parseChoice("kill moose", player));
    assertEquals(new Choice("hunt", player), Main.parseChoice("kill squirrel", player));
    assertEquals(new Choice("hunt", player), Main.parseChoice("kill rabbit", player));
    assertEquals(new Choice("hunt", player), Main.parseChoice("kill porcupine", player));
    assertEquals(new Choice("hunt", player), Main.parseChoice("get moose", player));
    assertEquals(new Choice("hunt", player), Main.parseChoice("get squirrel", player));
    assertEquals(new Choice("hunt", player), Main.parseChoice("get rabbit", player));
    assertEquals(new Choice("hunt", player), Main.parseChoice("get porcupine", player));
  }

  @Test
  public void testParseChoiceForage() {
    assertEquals(new Choice("forage", player), Main.parseChoice("forage", player));
    assertEquals(new Choice("forage", player), Main.parseChoice("hunt bug", player));
    assertEquals(new Choice("forage", player), Main.parseChoice("hunt bugs", player));
    assertEquals(new Choice("forage", player), Main.parseChoice("hunt mushrooms", player));
    assertEquals(new Choice("forage", player), Main.parseChoice("hunt mushroom", player));
    assertEquals(new Choice("forage", player), Main.parseChoice("hunt berry", player));
    assertEquals(new Choice("forage", player), Main.parseChoice("hunt berries", player));
    assertEquals(new Choice("forage", player), Main.parseChoice("get bug", player));
    assertEquals(new Choice("forage", player), Main.parseChoice("get bugs", player));
    assertEquals(new Choice("forage", player), Main.parseChoice("get mushrooms", player));
    assertEquals(new Choice("forage", player), Main.parseChoice("get mushroom", player));
    assertEquals(new Choice("forage", player), Main.parseChoice("get berry", player));
    assertEquals(new Choice("forage", player), Main.parseChoice("get berries", player));
    assertEquals(new Choice("forage", player), Main.parseChoice("kill bug", player));
    assertEquals(new Choice("forage", player), Main.parseChoice("kill bugs", player));
    assertEquals(new Choice("forage", player), Main.parseChoice("kill mushrooms", player));
    assertEquals(new Choice("forage", player), Main.parseChoice("kill mushroom", player));
    assertEquals(new Choice("forage", player), Main.parseChoice("kill berry", player));
    assertEquals(new Choice("forage", player), Main.parseChoice("kill berries", player));
  }

  @Test
  public void testParseChoiceTrap() {
    assertEquals(new Choice("trap", player), Main.parseChoice("trap", player));
    assertEquals(new Choice("trap", player), Main.parseChoice("trap squirrel", player));
    assertEquals(new Choice("trap", player), Main.parseChoice("go trap", player));
    assertEquals(new Choice("trap", player), Main.parseChoice("trapping", player));
    assertEquals(new Choice("trap", player), Main.parseChoice("go trapping", player));
    assertEquals(new Choice("trap", player), Main.parseChoice("trap moose", player));
    assertEquals(new Choice("trap", player), Main.parseChoice("trap rabbit", player));
    assertEquals(new Choice("trap", player), Main.parseChoice("trap porcupine", player));
  }

  @Test
  public void testParseChoiceFish() {
    assertEquals(new Choice("fish", player), Main.parseChoice("fish", player));
    assertEquals(new Choice("fish", player), Main.parseChoice("fishing", player));
    assertEquals(new Choice("fish", player), Main.parseChoice("go fish", player));
    assertEquals(new Choice("fish", player), Main.parseChoice("go fishing", player));
  }

  @Test
  public void testParseChoiceGet() {
    assertEquals(new Choice("get", player, Item.FISHING_LINE), Main.parseChoice("get fishing line", player));
    assertEquals(new Choice("get", player, Item.FISHING_LINE), Main.parseChoice("get fishing lines", player));
    assertEquals(new Choice("get", player, Item.FISHING_HOOKS), Main.parseChoice("get fishing hooks", player));
    assertEquals(new Choice("get", player, Item.FISHING_HOOKS), Main.parseChoice("get fishing hook", player));
    assertEquals(new Choice("get", player, Item.FISHING_LURES), Main.parseChoice("get fishing lure", player));
    assertEquals(new Choice("get", player, Item.FISHING_LURES), Main.parseChoice("get fishing lures", player));
    assertEquals(new Choice("get", player, Item.KNIFE), Main.parseChoice("get knife", player));
    assertEquals(new Choice("get", player, Item.FLINT_AND_STEEL), Main.parseChoice("get flint and steel", player));
    assertEquals(new Choice("get", player, Item.BOW), Main.parseChoice("get bow", player));
    assertEquals(new Choice("get", player, Item.ARROWS), Main.parseChoice("get arrow", player));
    assertEquals(new Choice("get", player, Item.ARROWS), Main.parseChoice("get arrows", player));
    assertEquals(new Choice("get", player, Item.FAMILY_PHOTO), Main.parseChoice("get family photo", player));
    assertEquals(new Choice("get", player, Item.FAMILY_PHOTO), Main.parseChoice("get photo", player));
    assertEquals(new Choice("get", player, Item.FAMILY_PHOTO), Main.parseChoice("get photograph", player));
    assertEquals(new Choice("get", player, Item.PARACHUTE_CHORD), Main.parseChoice("get rope", player));
    assertEquals(new Choice("get", player, Item.PARACHUTE_CHORD), Main.parseChoice("get parachute chord", player));
    assertEquals(new Choice("get", player, Item.PARACHUTE_CHORD), Main.parseChoice("get cordage", player));
    assertEquals(new Choice("get", player, Item.FLARE), Main.parseChoice("get flare", player));
    assertEquals(new Choice("get", player, Item.FLARE), Main.parseChoice("get flares", player));
    assertEquals(new Choice("get", player, Item.EXTRA_BOOTS), Main.parseChoice("get extra boots", player));
    assertEquals(new Choice("get", player, Item.EXTRA_BOOTS), Main.parseChoice("get extra boot", player));
    assertEquals(new Choice("get", player, Item.EXTRA_BOOTS), Main.parseChoice("get boots", player));
    assertEquals(new Choice("get", player, Item.EXTRA_BOOTS), Main.parseChoice("get boot", player));
    assertEquals(new Choice("get", player, Item.EXTRA_PANTS), Main.parseChoice("get extra pants", player));
    assertEquals(new Choice("get", player, Item.EXTRA_PANTS), Main.parseChoice("get pants", player));
    assertEquals(new Choice("get", player, Item.SLEEPING_GEAR), Main.parseChoice("get sleeping gear", player));
    assertEquals(new Choice("get", player, Item.SLEEPING_GEAR), Main.parseChoice("get sleeping bag", player));
    assertEquals(new Choice("get", player, Item.COLD_WEATHER_GEAR), Main.parseChoice("get cold weather gear", player));
    assertEquals(new Choice("get", player, Item.COLD_WEATHER_GEAR), Main.parseChoice("get cold gear", player));
    assertEquals(new Choice("get", player, Item.TARP), Main.parseChoice("get tarp", player));
    assertEquals(new Choice("get", player, Item.MATCHES), Main.parseChoice("get matches", player));
    assertEquals(new Choice("get", player, Item.MATCHES), Main.parseChoice("get match", player));
    assertEquals(new Choice("get", player, Item.FIRST_AID_KIT), Main.parseChoice("get first aid kit", player));
    assertEquals(new Choice("get", player, Item.FIRST_AID_KIT), Main.parseChoice("get first aid", player));
    assertEquals(new Choice("get", player, Item.FLASHLIGHT), Main.parseChoice("get flashlight", player));
    assertEquals(new Choice("get", player, Item.FLASHLIGHT), Main.parseChoice("get light", player));
    assertEquals(new Choice("get", player, Item.BATTERIES), Main.parseChoice("get batteries", player));
    assertEquals(new Choice("get", player, Item.BATTERIES), Main.parseChoice("get battery", player));
    assertEquals(new Choice("get", player, Item.WIRE), Main.parseChoice("get wire", player));
    assertEquals(new Choice("get", player, Item.WIRE), Main.parseChoice("get wires", player));
    assertEquals(new Choice("get", player, Item.WIRE), Main.parseChoice("get 18 gauge wire", player));
    assertEquals(new Choice("get", player, Item.WIRE), Main.parseChoice("get snare", player));
    assertEquals(new Choice("get", player, Item.WIRE), Main.parseChoice("get 18 gauge wires", player));
    assertEquals(new Choice("get", player, Item.POT), Main.parseChoice("get pot", player));
    assertEquals(new Choice("get", player, Item.POT), Main.parseChoice("get cooking pot", player));
    assertEquals(new Choice("get", player, Item.AXE), Main.parseChoice("get axe", player));
    assertEquals(new Choice("get", player, Item.HATCHET), Main.parseChoice("get hatchet", player));
    assertEquals(new Choice("get", player, Item.IODINE_TABLETS), Main.parseChoice("get iodine tablets", player));
    assertEquals(new Choice("get", player, Item.IODINE_TABLETS), Main.parseChoice("get iodine", player));
    assertEquals(new Choice("get", player, Item.IODINE_TABLETS), Main.parseChoice("get tablets", player));
    assertEquals(new Choice("get", player, Item.PISTOL), Main.parseChoice("get pistol", player));
    assertEquals(new Choice("get", player, Item.PISTOL), Main.parseChoice("get gun", player));
    assertEquals(new Choice("get", player, Item.PISTOL_CARTRIDGES), Main.parseChoice("get ammunition", player));
    assertEquals(new Choice("get", player, Item.PISTOL_CARTRIDGES), Main.parseChoice("get cartridges", player));
    assertEquals(new Choice("get", player, Item.PISTOL_CARTRIDGES), Main.parseChoice("get ammo", player));
    assertEquals(new Choice("get", player, Item.PISTOL_CARTRIDGES), Main.parseChoice("get rounds", player));
    assertEquals(new Choice("get", player, Item.SHOVEL), Main.parseChoice("get shovel", player));
    assertEquals(new Choice("get", player, Item.HARMONICA), Main.parseChoice("get harmonica", player));
    assertEquals(new Choice("get", player, Item.LIGHTER), Main.parseChoice("get lighter", player));
    assertEquals(new Choice("get", player, Item.SURVIVAL_MANUAL), Main.parseChoice("get survival manual", player));
    assertEquals(new Choice("get", player, Item.SURVIVAL_MANUAL), Main.parseChoice("get manual", player));
    assertEquals(new Choice("get", player, Item.JOURNAL), Main.parseChoice("get journal and pen", player));
    assertEquals(new Choice("get", player, Item.JOURNAL), Main.parseChoice("get journal", player));
  }

  @Test
  public void parseActivityChoice() {}

  @Test
  public void isPlayerDead() {}

  @Test
  public void isPlayerRescued() {}
}