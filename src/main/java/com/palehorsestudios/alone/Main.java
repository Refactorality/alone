package com.palehorsestudios.alone;

import static com.palehorsestudios.alone.Item.*;

import com.google.common.collect.Maps;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {
  public static void getNarrative(File file) {
    try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(file)))) {
      stream.forEach(System.out::println);
    } catch (IOException e) {
      System.out.println(
          "Whoops! We seemed to have misplaced the next segment of the story. We're working on it!");
    }
}

  public static void getInitialItems() throws NoSuchElementException {
    //print itemselection.txt
    //get player input to fill items map
    //when item is selected, print notification "x item selected"
    //ensure only 10 items are selected
    //do not iterate counter unless valid item is selected
    //
    //entry validation
    //call next scenario
    Scanner input = new Scanner(System.in);
    Map items = Maps.newHashMap();
    for (int i = 0; i < 10; i++) {
      String getItems = input.nextLine();
      if (getItems.contentEquals("1")) {
        if (items.containsKey(FISHING_LINE)) { System.out.println("You already have " + FISHING_LINE);
        i -= 1;}
        else { items.putIfAbsent(FISHING_LINE, 100);
        System.out.println("You put " + FISHING_LINE + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("2")) {
        if (items.containsKey(FISHING_HOOKS)) { System.out.println("You already have " + FISHING_HOOKS);
          i -= 1;}
        else { items.putIfAbsent(FISHING_HOOKS, 5);
          System.out.println("You put " + FISHING_HOOKS + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("3")) {
        if (items.containsKey(FISHING_LURES)) { System.out.println("You already have " + FISHING_LURES);
          i -= 1;}
        else { items.putIfAbsent(FISHING_LURES, 5);
          System.out.println("You put " + FISHING_LURES + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("4")) {
        if (items.containsKey(KNIFE)) { System.out.println("You already have " + KNIFE);
          i -= 1;}
        else { items.putIfAbsent(KNIFE, 1);
          System.out.println("You put " + KNIFE + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("5")) {
        if (items.containsKey(FLINT_AND_STEEL)) { System.out.println("You already have " + FLINT_AND_STEEL);
          i -= 1;}
        else { items.putIfAbsent(FLINT_AND_STEEL, 1);
          System.out.println("You put " + FLINT_AND_STEEL + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("6")) {
        if (items.containsKey(BOW)) { System.out.println("You already have " + BOW);
          i -= 1;}
        else { items.putIfAbsent(BOW, 1);
          System.out.println("You put " + BOW + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("7")) {
        if (items.containsKey(ARROWS)) { System.out.println("You already have " + ARROWS);
          i -= 1;}
        else { items.putIfAbsent(ARROWS, 10);
          System.out.println("You put " + ARROWS + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("8")) {
        if (items.containsKey(FAMILY_PHOTO)) { System.out.println("You already have " + FAMILY_PHOTO);
          i -= 1;}
        else { items.putIfAbsent(FAMILY_PHOTO, 1);
          System.out.println("You put " + FAMILY_PHOTO + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("9")) {
        if (items.containsKey(PARACHUTE_CHORD)) { System.out.println("You already have " + PARACHUTE_CHORD);
          i -= 1;}
        else { items.putIfAbsent(PARACHUTE_CHORD, 100);
          System.out.println("You put " + PARACHUTE_CHORD + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("10")) {
        if (items.containsKey(FLARE)) { System.out.println("You already have " + FLARE);
          i -= 1;}
        else { items.putIfAbsent(FLARE, 1);
          System.out.println("You put " + FLARE + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("11")) {
        if (items.containsKey(EXTRA_BOOTS)) { System.out.println("You already have " + EXTRA_BOOTS);
          i -= 1;}
        else { items.putIfAbsent(EXTRA_BOOTS, 1);
          System.out.println("You put " + EXTRA_BOOTS + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("12")) {
        if (items.containsKey(EXTRA_PANTS)) { System.out.println("You already have " + EXTRA_PANTS);
          i -= 1;}
        else { items.putIfAbsent(EXTRA_PANTS, 1);
          System.out.println("You put " + EXTRA_PANTS + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("13")) {
        if (items.containsKey(SLEEPING_GEAR)) { System.out.println("You already have " + SLEEPING_GEAR);
          i -= 1;}
        else { items.putIfAbsent(SLEEPING_GEAR, 1);
          System.out.println("You put " + SLEEPING_GEAR + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("14")) {
        if (items.containsKey(COLD_WEATHER_GEAR)) { System.out.println("You already have " + COLD_WEATHER_GEAR);
          i -= 1;}
        else { items.putIfAbsent(COLD_WEATHER_GEAR, 1);
          System.out.println("You put " + COLD_WEATHER_GEAR + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("15")) {
        if (items.containsKey(TARP)) { System.out.println("You already have " + TARP);
          i -= 1;}
        else { items.putIfAbsent(TARP, 1);
          System.out.println("You put " + TARP + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("16")) {
        if (items.containsKey(MATCHES)) { System.out.println("You already have " + MATCHES);
          i -= 1;}
        else { items.putIfAbsent(MATCHES, 100);
          System.out.println("You put " + MATCHES + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("17")) {
        if (items.containsKey(FIRST_AID_KIT)) { System.out.println("You already have " + FIRST_AID_KIT);
          i -= 1;}
        else { items.putIfAbsent(FIRST_AID_KIT, 1);
          System.out.println("You put " + FIRST_AID_KIT + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("18")) {
        if (items.containsKey(FLASHLIGHT)) { System.out.println("You already have " + FLASHLIGHT);
          i -= 1;}
        else { items.putIfAbsent(FLASHLIGHT, 1);
          System.out.println("You put " + FLASHLIGHT + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("19")) {
        if (items.containsKey(BATTERIES)) { System.out.println("You already have " + BATTERIES);
          i -= 1;}
        else { items.putIfAbsent(BATTERIES, 6);
          System.out.println("You put " + BATTERIES + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("20")) {
        if (items.containsKey(WIRE)) { System.out.println("You already have " + WIRE);
          i -= 1;}
        else { items.putIfAbsent(WIRE, 20);
          System.out.println("You put " + WIRE + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("21")) {
        if (items.containsKey(POT)) { System.out.println("You already have " + POT);
          i -= 1;}
        else { items.putIfAbsent(POT, 1);
          System.out.println("You put " + POT + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("22")) {
        if (items.containsKey(AXE)) { System.out.println("You already have " + AXE);
          i -= 1;}
        else { items.putIfAbsent(AXE, 1);
          System.out.println("You put " + AXE + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("23")) {
        if (items.containsKey(HATCHET)) { System.out.println("You already have " + HATCHET);
          i -= 1;}
        else { items.putIfAbsent(HATCHET, 1);
          System.out.println("You put " + HATCHET + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("24")) {
        if (items.containsKey(IODINE_TABLETS)) { System.out.println("You already have " + IODINE_TABLETS);
          i -= 1;}
        else { items.putIfAbsent(IODINE_TABLETS, 10);
          System.out.println("You put " + IODINE_TABLETS + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("25")) {
        if (items.containsKey(PISTOL)) { System.out.println("You already have " + PISTOL);
          i -= 1;}
        else { items.putIfAbsent(PISTOL, 1);
          System.out.println("You put " + PISTOL + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("26")) {
        if (items.containsKey(PISTOL_CARTRIDGES)) { System.out.println("You already have " + PISTOL_CARTRIDGES);
          i -= 1;}
        else { items.putIfAbsent(PISTOL_CARTRIDGES, 12);
          System.out.println("You put " + PISTOL_CARTRIDGES + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("27")) {
        if (items.containsKey(SHOVEL)) { System.out.println("You already have " + SHOVEL);
          i -= 1;}
        else { items.putIfAbsent(SHOVEL, 1);
          System.out.println("You put " + SHOVEL + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("28")) {
        if (items.containsKey(HARMONICA)) { System.out.println("You already have " + HARMONICA);
          i -= 1;}
        else { items.putIfAbsent(HARMONICA, 1);
          System.out.println("You put " + HARMONICA + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("29")) {
        if (items.containsKey(LIGHTER)) { System.out.println("You already have " + LIGHTER);
          i -= 1;}
        else { items.putIfAbsent(LIGHTER, 10);
          System.out.println("You put " + LIGHTER + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("30")) {
        if (items.containsKey(SURVIVAL_MANUAL)) { System.out.println("You already have " + SURVIVAL_MANUAL);
          i -= 1;}
        else { items.putIfAbsent(SURVIVAL_MANUAL, 1);
          System.out.println("You put " + SURVIVAL_MANUAL + " in your bag. " + (9 - i) + " items remaining");}
      } else if (getItems.contentEquals("31")) {
        if (items.containsKey(JOURNAL)) { System.out.println("You already have " + JOURNAL);
          i -= 1;}
        else { items.putIfAbsent(JOURNAL, 1);
          System.out.println("You put " + JOURNAL + " in your bag. " + (9 - i) + " items remaining");}
      }
      else { System.out.println("Whoops! It doesn't seem like you can get that! \n"
        + "Hurry and select from the stuff you know you can grab before the ship goes down!");
      i -= 1;}
      }
    System.out.println(items);
}
  public static void main(String[] args){
    // Main method that runs the game

    getNarrative(new File("resources/intronarrative.txt")); //initiates intro narrative
    getNarrative(new File("resources/itemselection.txt")); //grabs item selection text
    getInitialItems();
    getNarrative(new File("resources/scene1.txt"));
    
    //    String pInput = PlayerInput.getInstance().getString("Get items");
    // setup hashmap/method in main to get player selected items, then append items hashmap
    // to player when Player is instantiated.
  }
}
