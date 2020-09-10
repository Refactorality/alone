package com.palehorsestudios.alone;

import com.palehorsestudios.alone.activity.BoostMoraleActivity;
import com.palehorsestudios.alone.activity.BuildFireActivity;
import com.palehorsestudios.alone.activity.DrinkWaterActivity;
import com.palehorsestudios.alone.activity.EatActivity;
import com.palehorsestudios.alone.activity.FishActivity;
import com.palehorsestudios.alone.activity.ForageActivity;
import com.palehorsestudios.alone.activity.GatherFirewoodActivity;
import com.palehorsestudios.alone.activity.GetItemActivity;
import com.palehorsestudios.alone.activity.GetWaterActivity;
import com.palehorsestudios.alone.activity.HuntActivity;
import com.palehorsestudios.alone.activity.ImproveShelterActivity;
import com.palehorsestudios.alone.activity.PutItemActivity;
import com.palehorsestudios.alone.activity.RestActivity;
import com.palehorsestudios.alone.activity.TrapActivity;
import com.palehorsestudios.alone.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.palehorsestudios.alone.Main.parseActivityChoice;
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
    assertEquals(new Choice("eat", player, Food.MOOSE), Main.parseChoice("eat moose", player));
    assertEquals(new Choice("eat", player, Food.FISH), Main.parseChoice("eat fish", player));
    assertEquals(new Choice("eat", player, Food.SQUIRREL), Main.parseChoice("eat squirrel", player));
    assertEquals(new Choice("eat", player, Food.BEAR), Main.parseChoice("eat bear", player));
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
    assertEquals(new Choice("hunt", player), Main.parseChoice("go hunt", player));
    assertEquals(new Choice("hunt", player), Main.parseChoice("go hunting", player));
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
    assertEquals(new Choice("forage", player), Main.parseChoice("go forage", player));
    assertEquals(new Choice("forage", player), Main.parseChoice("go foraging", player));
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
  public void testParseChoicePut() {
    assertEquals(new Choice("put", player, Item.FISHING_LINE), Main.parseChoice("put fishing line", player));
    assertEquals(new Choice("put", player, Item.FISHING_LINE), Main.parseChoice("put fishing lines", player));
    assertEquals(new Choice("put", player, Item.FISHING_HOOKS), Main.parseChoice("put fishing hooks", player));
    assertEquals(new Choice("put", player, Item.FISHING_HOOKS), Main.parseChoice("put fishing hook", player));
    assertEquals(new Choice("put", player, Item.FISHING_LURES), Main.parseChoice("put fishing lure", player));
    assertEquals(new Choice("put", player, Item.FISHING_LURES), Main.parseChoice("put fishing lures", player));
    assertEquals(new Choice("put", player, Item.KNIFE), Main.parseChoice("put knife", player));
    assertEquals(new Choice("put", player, Item.FLINT_AND_STEEL), Main.parseChoice("put flint and steel", player));
    assertEquals(new Choice("put", player, Item.BOW), Main.parseChoice("put bow", player));
    assertEquals(new Choice("put", player, Item.ARROWS), Main.parseChoice("put arrow", player));
    assertEquals(new Choice("put", player, Item.ARROWS), Main.parseChoice("put arrows", player));
    assertEquals(new Choice("put", player, Item.FAMILY_PHOTO), Main.parseChoice("put family photo", player));
    assertEquals(new Choice("put", player, Item.FAMILY_PHOTO), Main.parseChoice("put photo", player));
    assertEquals(new Choice("put", player, Item.FAMILY_PHOTO), Main.parseChoice("put photograph", player));
    assertEquals(new Choice("put", player, Item.PARACHUTE_CHORD), Main.parseChoice("put rope", player));
    assertEquals(new Choice("put", player, Item.PARACHUTE_CHORD), Main.parseChoice("put parachute chord", player));
    assertEquals(new Choice("put", player, Item.PARACHUTE_CHORD), Main.parseChoice("put cordage", player));
    assertEquals(new Choice("put", player, Item.FLARE), Main.parseChoice("put flare", player));
    assertEquals(new Choice("put", player, Item.FLARE), Main.parseChoice("put flares", player));
    assertEquals(new Choice("put", player, Item.EXTRA_BOOTS), Main.parseChoice("put extra boots", player));
    assertEquals(new Choice("put", player, Item.EXTRA_BOOTS), Main.parseChoice("put extra boot", player));
    assertEquals(new Choice("put", player, Item.EXTRA_BOOTS), Main.parseChoice("put boots", player));
    assertEquals(new Choice("put", player, Item.EXTRA_BOOTS), Main.parseChoice("put boot", player));
    assertEquals(new Choice("put", player, Item.EXTRA_PANTS), Main.parseChoice("put extra pants", player));
    assertEquals(new Choice("put", player, Item.EXTRA_PANTS), Main.parseChoice("put pants", player));
    assertEquals(new Choice("put", player, Item.SLEEPING_GEAR), Main.parseChoice("put sleeping gear", player));
    assertEquals(new Choice("put", player, Item.SLEEPING_GEAR), Main.parseChoice("put sleeping bag", player));
    assertEquals(new Choice("put", player, Item.COLD_WEATHER_GEAR), Main.parseChoice("put cold weather gear", player));
    assertEquals(new Choice("put", player, Item.COLD_WEATHER_GEAR), Main.parseChoice("put cold gear", player));
    assertEquals(new Choice("put", player, Item.TARP), Main.parseChoice("put tarp", player));
    assertEquals(new Choice("put", player, Item.MATCHES), Main.parseChoice("put matches", player));
    assertEquals(new Choice("put", player, Item.MATCHES), Main.parseChoice("put match", player));
    assertEquals(new Choice("put", player, Item.FIRST_AID_KIT), Main.parseChoice("put first aid kit", player));
    assertEquals(new Choice("put", player, Item.FIRST_AID_KIT), Main.parseChoice("put first aid", player));
    assertEquals(new Choice("put", player, Item.FLASHLIGHT), Main.parseChoice("put flashlight", player));
    assertEquals(new Choice("put", player, Item.FLASHLIGHT), Main.parseChoice("put light", player));
    assertEquals(new Choice("put", player, Item.BATTERIES), Main.parseChoice("put batteries", player));
    assertEquals(new Choice("put", player, Item.BATTERIES), Main.parseChoice("put battery", player));
    assertEquals(new Choice("put", player, Item.WIRE), Main.parseChoice("put wire", player));
    assertEquals(new Choice("put", player, Item.WIRE), Main.parseChoice("put wires", player));
    assertEquals(new Choice("put", player, Item.WIRE), Main.parseChoice("put 18 gauge wire", player));
    assertEquals(new Choice("put", player, Item.WIRE), Main.parseChoice("put snare", player));
    assertEquals(new Choice("put", player, Item.WIRE), Main.parseChoice("put 18 gauge wires", player));
    assertEquals(new Choice("put", player, Item.POT), Main.parseChoice("put pot", player));
    assertEquals(new Choice("put", player, Item.POT), Main.parseChoice("put cooking pot", player));
    assertEquals(new Choice("put", player, Item.AXE), Main.parseChoice("put axe", player));
    assertEquals(new Choice("put", player, Item.HATCHET), Main.parseChoice("put hatchet", player));
    assertEquals(new Choice("put", player, Item.IODINE_TABLETS), Main.parseChoice("put iodine tablets", player));
    assertEquals(new Choice("put", player, Item.IODINE_TABLETS), Main.parseChoice("put iodine", player));
    assertEquals(new Choice("put", player, Item.IODINE_TABLETS), Main.parseChoice("put tablets", player));
    assertEquals(new Choice("put", player, Item.PISTOL), Main.parseChoice("put pistol", player));
    assertEquals(new Choice("put", player, Item.PISTOL), Main.parseChoice("put gun", player));
    assertEquals(new Choice("put", player, Item.PISTOL_CARTRIDGES), Main.parseChoice("put ammunition", player));
    assertEquals(new Choice("put", player, Item.PISTOL_CARTRIDGES), Main.parseChoice("put cartridges", player));
    assertEquals(new Choice("put", player, Item.PISTOL_CARTRIDGES), Main.parseChoice("put ammo", player));
    assertEquals(new Choice("put", player, Item.PISTOL_CARTRIDGES), Main.parseChoice("put rounds", player));
    assertEquals(new Choice("put", player, Item.SHOVEL), Main.parseChoice("put shovel", player));
    assertEquals(new Choice("put", player, Item.HARMONICA), Main.parseChoice("put harmonica", player));
    assertEquals(new Choice("put", player, Item.LIGHTER), Main.parseChoice("put lighter", player));
    assertEquals(new Choice("put", player, Item.SURVIVAL_MANUAL), Main.parseChoice("put survival manual", player));
    assertEquals(new Choice("put", player, Item.SURVIVAL_MANUAL), Main.parseChoice("put manual", player));
    assertEquals(new Choice("put", player, Item.JOURNAL), Main.parseChoice("put journal and pen", player));
    assertEquals(new Choice("put", player, Item.JOURNAL), Main.parseChoice("put journal", player));
  }

  @Test
  public void testParseChoiceImprove() {
    assertEquals(new Choice("improve", player), Main.parseChoice("build shelter", player));
    assertEquals(new Choice("improve", player), Main.parseChoice("make camp", player));
    assertEquals(new Choice("improve", player), Main.parseChoice("work on camp", player));
    assertEquals(new Choice("improve", player), Main.parseChoice("work on shelter", player));
    assertEquals(new Choice("improve", player), Main.parseChoice("improve shelter", player));
    assertEquals(new Choice("improve", player), Main.parseChoice("improve camp", player));
    assertEquals(new Choice("improve", player), Main.parseChoice("build camp", player));
  }

  @Test
  public void testParseChoiceDrink() {
    assertEquals(new Choice("drink", player), Main.parseChoice("drink", player));
    assertEquals(new Choice("drink", player), Main.parseChoice("drink water", player));
    assertEquals(new Choice("drink", player), Main.parseChoice("get a drink", player));
    assertEquals(new Choice("drink", player), Main.parseChoice("take a drink", player));
    assertEquals(new Choice("drink", player), Main.parseChoice("get drink", player));
    assertEquals(new Choice("drink", player), Main.parseChoice("take drink", player));
  }

  @Test
  public void testParseChoiceGather() {
    assertEquals(new Choice("gather", player), Main.parseChoice("gather", player));
    assertEquals(new Choice("gather", player), Main.parseChoice("gather firewood", player));
    assertEquals(new Choice("gather", player), Main.parseChoice("get firewood", player));
    assertEquals(new Choice("gather", player), Main.parseChoice("collect firewood", player));
    assertEquals(new Choice("gather", player), Main.parseChoice("cut firewood", player));
    assertEquals(new Choice("gather", player), Main.parseChoice("gather wood", player));
    assertEquals(new Choice("gather", player), Main.parseChoice("get wood", player));
    assertEquals(new Choice("gather", player), Main.parseChoice("collect wood", player));
    assertEquals(new Choice("gather", player), Main.parseChoice("cut wood", player));
  }

  @Test
  public void testParseChoiceFire() {
    assertEquals(new Choice("fire", player), Main.parseChoice("fire", player));
    assertEquals(new Choice("fire", player), Main.parseChoice("build fire", player));
    assertEquals(new Choice("fire", player), Main.parseChoice("light fire", player));
    assertEquals(new Choice("fire", player), Main.parseChoice("start fire", player));
    assertEquals(new Choice("fire", player), Main.parseChoice("make fire", player));
    assertEquals(new Choice("fire", player), Main.parseChoice("build a fire", player));
    assertEquals(new Choice("fire", player), Main.parseChoice("light a fire", player));
    assertEquals(new Choice("fire", player), Main.parseChoice("start a fire", player));
    assertEquals(new Choice("fire", player), Main.parseChoice("make a fire", player));
  }

  @Test
  public void testParseChoiceWater() {
    assertEquals(new Choice("water", player), Main.parseChoice("water", player));
    assertEquals(new Choice("water", player), Main.parseChoice("get water", player));
    assertEquals(new Choice("water", player), Main.parseChoice("fetch water", player));
    assertEquals(new Choice("water", player), Main.parseChoice("collect water", player));
  }

  @Test
  public void testParseChoiceMorale() {
    assertEquals(new Choice("morale", player), Main.parseChoice("morale", player));
    assertEquals(new Choice("morale", player), Main.parseChoice("improve morale", player));
    assertEquals(new Choice("morale", player), Main.parseChoice("boost morale", player));
    assertEquals(new Choice("morale", player), Main.parseChoice("photo", player));
    assertEquals(new Choice("morale", player), Main.parseChoice("look at photo", player));
    assertEquals(new Choice("morale", player), Main.parseChoice("look at family photo", player));
    assertEquals(new Choice("morale", player), Main.parseChoice("harmonica", player));
    assertEquals(new Choice("morale", player), Main.parseChoice("play harmonica", player));
    assertEquals(new Choice("morale", player), Main.parseChoice("journal", player));
    assertEquals(new Choice("morale", player), Main.parseChoice("write in journal", player));
  }

  @Test
  public void testParseChoiceRest() {
    assertEquals(new Choice("rest", player), Main.parseChoice("rest", player));
    assertEquals(new Choice("rest", player), Main.parseChoice("break", player));
    assertEquals(new Choice("rest", player), Main.parseChoice("take rest", player));
    assertEquals(new Choice("rest", player), Main.parseChoice("take break", player));
    assertEquals(new Choice("rest", player), Main.parseChoice("take a rest", player));
    assertEquals(new Choice("rest", player), Main.parseChoice("take a break", player));
    assertEquals(new Choice("rest", player), Main.parseChoice("relax", player));
    assertEquals(new Choice("rest", player), Main.parseChoice("nap", player));
    assertEquals(new Choice("rest", player), Main.parseChoice("take nap", player));
    assertEquals(new Choice("rest", player), Main.parseChoice("take a nap", player));
  }

  @Test
  public void testParseActivityChoiceGet() {
    for(Item item : Item.values()) {
      assertEquals(GetItemActivity.getInstance(), parseActivityChoice(new Choice("get", player, item)));
    }
  }

  @Test
  public void testParseActivityChoicePut() {
    for(Item item : Item.values()) {
      assertEquals(PutItemActivity.getInstance(), parseActivityChoice(new Choice("put", player, item)));
    }
  }

  @Test
  public void testParseActivityChoiceEat() {
    for(Food food : Food.values()) {
      assertEquals(EatActivity.getInstance(), parseActivityChoice(new Choice("eat", player, food)));
    }
  }

  @Test
  public void testParseActivityChoiceDrink() {
    assertEquals(DrinkWaterActivity.getInstance(), parseActivityChoice(new Choice("drink", player)));
  }

  @Test
  public void testParseActivityChoiceFish() {
    assertEquals(FishActivity.getInstance(), parseActivityChoice(new Choice("fish", player)));
  }

  @Test
  public void testParseActivityChoiceHunt() {
    assertEquals(HuntActivity.getInstance(), parseActivityChoice(new Choice("hunt", player)));
  }

  @Test
  public void testParseActivityChoiceTrap() {
    assertEquals(TrapActivity.getInstance(), parseActivityChoice(new Choice("trap", player)));
  }

  @Test
  public void testParseActivityChoiceForage() {
    assertEquals(ForageActivity.getInstance(), parseActivityChoice(new Choice("forage", player)));
  }

  @Test
  public void testParseActivityChoiceImprove() {
    assertEquals(ImproveShelterActivity.getInstance(), parseActivityChoice(new Choice("improve", player)));
  }

  @Test
  public void testParseActivityChoiceGather() {
    assertEquals(GatherFirewoodActivity.getInstance(), parseActivityChoice(new Choice("gather", player)));
  }

  @Test
  public void testParseActivityChoiceFire() {
    assertEquals(BuildFireActivity.getInstance(), parseActivityChoice(new Choice("fire", player)));
  }

  @Test
  public void testParseActivityChoiceWater() {
    assertEquals(GetWaterActivity.getInstance(), parseActivityChoice(new Choice("water", player)));
  }

  @Test
  public void testParseActivityChoiceMorale() {
    assertEquals(BoostMoraleActivity.getInstance(), parseActivityChoice(new Choice("morale", player)));
  }

  @Test
  public void testParseActivityChoiceRest() {
    assertEquals(RestActivity.getInstance(), parseActivityChoice(new Choice("rest", player)));
  }
}