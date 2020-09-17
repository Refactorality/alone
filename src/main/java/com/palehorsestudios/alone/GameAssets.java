package com.palehorsestudios.alone;


import com.palehorsestudios.alone.dayencounter.BearEncounterDay;
import com.palehorsestudios.alone.dayencounter.DayEncounter;
import com.palehorsestudios.alone.dayencounter.RescueHelicopterDay;
import com.palehorsestudios.alone.util.reader.EncounterReader;
import com.palehorsestudios.alone.util.reader.FoodReader;
import com.palehorsestudios.alone.util.reader.ItemReader;
import com.palehorsestudios.alone.util.reader.ResourcesRequiredReader;

import java.util.HashMap;
import java.util.Map;

public class GameAssets {
    public static HashMap<String, Item> gameItems;
    public static HashMap<String, Food> gameFoods;
    public static Map<String, String> choiceKeywordMap;
    public static Map<String, Food> choiceFoodMap;
    public static Map<String, Item> choiceItemMap;
    public static HashMap<String, DayEncounter> encounters;

    public void loadAssets() {
        loadItems();
        loadFood();
        addItemResourcesRequired();
        loadCommands();

        //needs to come after loadItems, uses instantiated items
        loadEncounters();
    }

    public static void loadItems() {
        gameItems = ItemReader.readItemsXML("./resources/xml/items.xml");
    }

    public static void loadFood() {
        gameFoods = FoodReader.readFoodsXML("./resources/xml/foods.xml");
    }

    public static void addItemResourcesRequired() {
        ResourcesRequiredReader.readRequiredResourcesXML("./resources/xml/requiredResources.xml");

        for (Item item : gameItems.values()) {
            if (item.getResourcesRequired() != null ) {
                for (Item resource : item.getResourcesRequired()) {
                    System.out.println(resource.getVisibleName());
                }
            }
        }

        for (Food food : gameFoods.values()) {
            if (food.getResourcesRequired() != null) {
                for (Item resource : food.getResourcesRequired()) {
                    System.out.println(resource.getVisibleName());
                }
            }
        }
    }

    public static void loadCommands() {
        choiceKeywordMap = new HashMap<>(){{
            put("eat moose", "eat");
            put("eat fish", "eat");
            put("eat squirrel", "eat");
            put("eat rabbit", "eat");
            put("eat porcupine", "eat");
            put("eat bug", "eat");
            put("eat bugs", "eat");
            put("eat mushroom", "eat");
            put("eat mushrooms", "eat");
            put("eat berry", "eat");
            put("eat berries", "eat");
            put("eat bear", "eat");
            put("go hunt", "hunt");
            put("go hunting", "hunt");
            put("hunt", "hunt");
            put("hunt moose", "hunt");
            put("hunt squirrel", "hunt");
            put("hunt rabbit", "hunt");
            put("hunt porcupine", "hunt");
            put("forage", "forage");
            put("go forage", "forage");
            put("go foraging", "forage");
            put("hunt bug", "forage");
            put("hunt mushrooms", "forage");
            put("hunt berry", "forage");
            put("hunt bugs", "forage");
            put("hunt mushroom", "forage");
            put("hunt berries", "forage");
            put("get moose", "hunt");
            put("get squirrel", "hunt");
            put("get rabbit", "hunt");
            put("get porcupine", "hunt");
            put("get bug", "forage");
            put("get mushrooms", "forage");
            put("get berry", "forage");
            put("get bugs", "forage");
            put("get mushroom", "forage");
            put("get berries", "forage");
            put("kill moose", "hunt");
            put("kill squirrel", "hunt");
            put("kill rabbit", "hunt");
            put("kill porcupine", "hunt");
            put("kill bug", "forage");
            put("kill mushrooms", "forage");
            put("kill berry", "forage");
            put("kill bugs", "forage");
            put("kill mushroom", "forage");
            put("kill berries", "forage");
            put("trap squirrel", "trap");
            put("trap moose", "trap");
            put("trap rabbit", "trap");
            put("trap porcupine", "trap");
            put("trap", "trap");
            put("trapping", "trap");
            put("go trap", "trap");
            put("go trapping", "trap");
            put("fish", "fish");
            put("fishing", "fish");
            put("go fish", "fish");
            put("cook fish", "make");
            put("go fishing", "fish");
            put("get fishing line", "get");
            put("get fishing lines", "get");
            put("get fishing hooks", "get");
            put("get fishing hook", "get");
            put("get fishing lure", "get");
            put("get fishing lures", "get");
            put("get knife", "get");
            put("get flint and steel", "get");
            put("get bow", "get");
            put("get arrow", "get");
            put("get arrows", "get");
            put("get family photo", "get");
            put("get photo", "get");
            put("get photograph", "get");
            put("get rope", "get");
            put("get parachute chord", "get");
            put("get cordage", "get");
            put("get flare", "get");
            put("get flares", "get");
            put("get extra boots", "get");
            put("get extra boot", "get");
            put("get boots", "get");
            put("get boot", "get");
            put("get extra pants", "get");
            put("get pants", "get");
            put("get sleeping gear", "get");
            put("get sleeping bag", "get");
            put("get cold weather gear", "get");
            put("get cold gear", "get");
            put("get tarp", "get");
            put("get matches", "get");
            put("get match", "get");
            put("get first aid", "get");
            put("get first aid kit", "get");
            put("get flashlight", "get");
            put("get light", "get");
            put("get batteries", "get");
            put("get battery", "get");
            put("get wire", "get");
            put("get 18 gauge wire", "get");
            put("get wires", "get");
            put("get 18 gauge wires", "get");
            put("get snare", "get");
            put("get pot", "get");
            put("get cooking pot", "get");
            put("get axe", "get");
            put("get hatchet", "get");
            put("get iodine tablets", "get");
            put("get iodine", "get");
            put("get tablets", "get");
            put("get pistol", "get");
            put("get gun", "get");
            put("get ammunition", "get");
            put("get cartridges", "get");
            put("get ammo", "get");
            put("get rounds", "get");
            put("get shovel", "get");
            put("get harmonica", "get");
            put("get lighter", "get");
            put("get survival manual", "get");
            put("get manual", "get");
            put("get journal and pen", "get");
            put("get journal", "get");
            put("put fishing line", "put");
            put("put fishing lines", "put");
            put("put fishing hooks", "put");
            put("put fishing hook", "put");
            put("put fishing lure", "put");
            put("put fishing lures", "put");
            put("put knife", "put");
            put("put flint and steel", "put");
            put("put bow", "put");
            put("put arrow", "put");
            put("put arrows", "put");
            put("put family photo", "put");
            put("put photo", "put");
            put("put photograph", "put");
            put("put rope", "put");
            put("put parachute chord", "put");
            put("put cordage", "put");
            put("put flare", "put");
            put("put flares", "put");
            put("put extra boots", "put");
            put("put extra boot", "put");
            put("put boots", "put");
            put("put boot", "put");
            put("put extra pants", "put");
            put("put pants", "put");
            put("put sleeping gear", "put");
            put("put sleeping bag", "put");
            put("put cold weather gear", "put");
            put("put cold gear", "put");
            put("put tarp", "put");
            put("put matches", "put");
            put("put match", "put");
            put("put first aid", "put");
            put("put first aid kit", "put");
            put("put flashlight", "put");
            put("put light", "put");
            put("put batteries", "put");
            put("put battery", "put");
            put("put wire", "put");
            put("put 18 gauge wire", "put");
            put("put wires", "put");
            put("put 18 gauge wires", "put");
            put("put snare", "put");
            put("put pot", "put");
            put("put cooking pot", "put");
            put("put axe", "put");
            put("put hatchet", "put");
            put("put iodine tablets", "put");
            put("put iodine", "put");
            put("put tablets", "put");
            put("put pistol", "put");
            put("put gun", "put");
            put("put ammunition", "put");
            put("put ammo", "put");
            put("put cartridges", "put");
            put("put rounds", "put");
            put("put shovel", "put");
            put("put harmonica", "put");
            put("put lighter", "put");
            put("put survival manual", "put");
            put("put manual", "put");
            put("put journal and pen", "put");
            put("put journal", "put");
            put("build shelter", "improve");
            put("make camp", "improve");
            put("work on camp", "improve");
            put("work on shelter", "improve");
            put("improve shelter", "improve");
            put("improve camp", "improve");
            put("build camp", "improve");
            put("drink", "drink");
            put("drink water", "drink");
            put("get drink", "drink");
            put("take drink", "drink");
            put("get a drink", "drink");
            put("take a drink", "drink");
            put("gather", "gather");
            put("gather firewood", "gather");
            put("get firewood", "gather");
            put("collect firewood", "gather");
            put("cut firewood", "gather");
            put("gather wood", "gather");
            put("get wood", "gather");
            put("collect wood", "gather");
            put("cut wood", "gather");
            put("fire", "fire");
            put("build fire", "fire");
            put("light fire", "fire");
            put("start fire", "fire");
            put("make fire", "fire");
            put("build a fire", "fire");
            put("light a fire", "fire");
            put("start a fire", "fire");
            put("make a fire", "fire");
            put("water", "water");
            put("get water", "water");
            put("fetch water", "water");
            put("collect water", "water");
            put("morale", "morale");
            put("improve morale", "morale");
            put("boost morale", "morale");
            put("photo", "morale");
            put("look at photo", "morale");
            put("look at family photo", "morale");
            put("harmonica", "morale");
            put("play harmonica", "morale");
            put("journal", "morale");
            put("write in journal", "morale");
            put("rest", "rest");
            put("break", "rest");
            put("take rest", "rest");
            put("take break", "rest");
            put("take a rest", "rest");
            put("take a break", "rest");
            put("relax", "rest");
            put("nap", "rest");
            put("take nap", "rest");
            put("take a nap", "rest");
            put("make bow", "make");
        }};

        choiceFoodMap = new HashMap<>(){{
            put("eat moose", gameFoods.get("MOOSE"));
            put("eat fish", gameFoods.get("FISH"));
            put("eat bear", gameFoods.get("BEAR"));
            put("eat squirrel", gameFoods.get("SQUIRREL"));
            put("eat rabbit", gameFoods.get("RABBIT"));
            put("eat porcupine", gameFoods.get("PORCUPINE"));
            put("eat bug", gameFoods.get("BUG"));
            put("eat bugs", gameFoods.get("BUG"));
            put("eat mushroom", gameFoods.get("MUSHROOM"));
            put("eat mushrooms", gameFoods.get("MUSHROOM"));
            put("eat berry", gameFoods.get("BERRIES"));
            put("eat berries", gameFoods.get("BERRIES"));
            put("cook fish", gameFoods.get("COOKED_FISH"));
        }};

        choiceItemMap = new HashMap<>(){{
            put("get fishing line", gameItems.get("FISHING_LINE"));
            put("get fishing lines", gameItems.get("FISHING_LINE"));
            put("get fishing hooks", gameItems.get("FISHING_HOOKS"));
            put("get fishing hook", gameItems.get("FISHING_HOOKS"));
            put("get fishing lure", gameItems.get("FISHING_LURES"));
            put("get fishing lures", gameItems.get("FISHING_LURES"));
            put("get knife", gameItems.get("KNIFE"));
            put("get flint and steel", gameItems.get("FLINT_AND_STEEL"));
            put("get bow", gameItems.get("BOW"));
            put("get arrow", gameItems.get("ARROWS"));
            put("get arrows", gameItems.get("ARROWS"));
            put("get family photo", gameItems.get("FAMILY_PHOTO"));
            put("get photo", gameItems.get("FAMILY_PHOTO"));
            put("get photograph", gameItems.get("FAMILY_PHOTO"));
            put("get rope", gameItems.get("PARACHUTE_CHORD"));
            put("get parachute chord", gameItems.get("PARACHUTE_CHORD"));
            put("get cordage", gameItems.get("PARACHUTE_CHORD"));
            put("get flare", gameItems.get("FLARE"));
            put("get flares", gameItems.get("FLARE"));
            put("get extra boots", gameItems.get("EXTRA_BOOTS"));
            put("get extra boot", gameItems.get("EXTRA_BOOTS"));
            put("get boots", gameItems.get("EXTRA_BOOTS"));
            put("get boot", gameItems.get("EXTRA_BOOTS"));
            put("get extra pants", gameItems.get("EXTRA_PANTS"));
            put("get pants", gameItems.get("EXTRA_PANTS"));
            put("get sleeping gear", gameItems.get("SLEEPING_GEAR"));
            put("get sleeping bag", gameItems.get("SLEEPING_GEAR"));
            put("get cold weather gear", gameItems.get("COLD_WEATHER_GEAR"));
            put("get cold gear", gameItems.get("COLD_WEATHER_GEAR"));
            put("get tarp", gameItems.get("TARP"));
            put("get matches", gameItems.get("MATCHES"));
            put("get match", gameItems.get("MATCHES"));
            put("get first aid", gameItems.get("FIRST_AID_KIT"));
            put("get first aid kit", gameItems.get("FIRST_AID_KIT"));
            put("get flashlight", gameItems.get("FLASHLIGHT"));
            put("get light", gameItems.get("FLASHLIGHT"));
            put("get batteries", gameItems.get("BATTERIES"));
            put("get battery", gameItems.get("BATTERIES"));
            put("get wire", gameItems.get("WIRE"));
            put("get 18 gauge wire", gameItems.get("WIRE"));
            put("get wires", gameItems.get("WIRE"));
            put("get 18 gauge wires", gameItems.get("WIRE"));
            put("get snare", gameItems.get("WIRE"));
            put("get pot", gameItems.get("POT"));
            put("get cooking pot", gameItems.get("POT"));
            put("get axe", gameItems.get("AXE"));
            put("get hatchet", gameItems.get("HATCHET"));
            put("get iodine tablets", gameItems.get("IODINE_TABLETS"));
            put("get iodine", gameItems.get("IODINE_TABLETS"));
            put("get tablets", gameItems.get("IODINE_TABLETS"));
            put("get pistol", gameItems.get("PISTOL"));
            put("get gun", gameItems.get("PISTOL"));
            put("get ammunition", gameItems.get("PISTOL_CARTRIDGES"));
            put("get cartridges", gameItems.get("PISTOL_CARTRIDGES"));
            put("get ammo", gameItems.get("PISTOL_CARTRIDGES"));
            put("get rounds", gameItems.get("PISTOL_CARTRIDGES"));
            put("get pistol ammo", gameItems.get("PISTOL_CARTRIDGES"));
            put("get pistol ammunition", gameItems.get("PISTOL_CARTRIDGES"));
            put("get pistol rounds", gameItems.get("PISTOL_CARTRIDGES"));
            put("get pistol cartridges", gameItems.get("PISTOL_CARTRIDGES"));
            put("get shovel", gameItems.get("SHOVEL"));
            put("get harmonica", gameItems.get("HARMONICA"));
            put("get lighter", gameItems.get("LIGHTER"));
            put("get survival manual", gameItems.get("SURVIVAL_MANUAL"));
            put("get manual", gameItems.get("SURVIVAL_MANUAL"));
            put("get journal and pen", gameItems.get("JOURNAL"));
            put("get journal", gameItems.get("JOURNAL"));
            put("put fishing line", gameItems.get("FISHING_LINE"));
            put("put fishing lines", gameItems.get("FISHING_LINE"));
            put("put fishing hooks", gameItems.get("FISHING_HOOKS"));
            put("put fishing hook", gameItems.get("FISHING_HOOKS"));
            put("put fishing lure", gameItems.get("FISHING_LURES"));
            put("put fishing lures", gameItems.get("FISHING_LURES"));
            put("put knife", gameItems.get("KNIFE"));
            put("put flint and steel", gameItems.get("FLINT_AND_STEEL"));
            put("put bow", gameItems.get("BOW"));
            put("put arrow", gameItems.get("ARROWS"));
            put("put arrows", gameItems.get("ARROWS"));
            put("put family photo", gameItems.get("FAMILY_PHOTO"));
            put("put photo", gameItems.get("FAMILY_PHOTO"));
            put("put photograph", gameItems.get("FAMILY_PHOTO"));
            put("put rope", gameItems.get("PARACHUTE_CHORD"));
            put("put parachute chord", gameItems.get("PARACHUTE_CHORD"));
            put("put cordage", gameItems.get("PARACHUTE_CHORD"));
            put("put flare", gameItems.get("FLARE"));
            put("put flares", gameItems.get("FLARE"));
            put("put extra boots", gameItems.get("EXTRA_BOOTS"));
            put("put extra boot", gameItems.get("EXTRA_BOOTS"));
            put("put boots", gameItems.get("EXTRA_BOOTS"));
            put("put boot", gameItems.get("EXTRA_BOOTS"));
            put("put extra pants", gameItems.get("EXTRA_PANTS"));
            put("put pants", gameItems.get("EXTRA_PANTS"));
            put("put sleeping gear", gameItems.get("SLEEPING_GEAR"));
            put("put sleeping bag", gameItems.get("SLEEPING_GEAR"));
            put("put cold weather gear", gameItems.get("COLD_WEATHER_GEAR"));
            put("put cold gear", gameItems.get("COLD_WEATHER_GEAR"));
            put("put tarp", gameItems.get("TARP"));
            put("put matches", gameItems.get("MATCHES"));
            put("put match", gameItems.get("MATCHES"));
            put("put first aid", gameItems.get("FIRST_AID_KIT"));
            put("put first aid kit", gameItems.get("FIRST_AID_KIT"));
            put("put flashlight", gameItems.get("FLASHLIGHT"));
            put("put light", gameItems.get("FLASHLIGHT"));
            put("put batteries", gameItems.get("BATTERIES"));
            put("put battery", gameItems.get("BATTERIES"));
            put("put wire", gameItems.get("WIRE"));
            put("put 18 gauge wire", gameItems.get("WIRE"));
            put("put wires", gameItems.get("WIRE"));
            put("put 18 gauge wires", gameItems.get("WIRE"));
            put("put snare", gameItems.get("WIRE"));
            put("put pot", gameItems.get("POT"));
            put("put cooking pot", gameItems.get("POT"));
            put("put axe", gameItems.get("AXE"));
            put("put hatchet", gameItems.get("HATCHET"));
            put("put iodine tablets", gameItems.get("IODINE_TABLETS"));
            put("put iodine", gameItems.get("IODINE_TABLETS"));
            put("put tablets", gameItems.get("IODINE_TABLETS"));
            put("put pistol", gameItems.get("PISTOL"));
            put("put gun", gameItems.get("PISTOL"));
            put("put ammunition", gameItems.get("PISTOL_CARTRIDGES"));
            put("put ammo", gameItems.get("PISTOL_CARTRIDGES"));
            put("put pistol ammo", gameItems.get("PISTOL_CARTRIDGES"));
            put("put pistol ammunition", gameItems.get("PISTOL_CARTRIDGES"));
            put("put pistol rounds", gameItems.get("PISTOL_CARTRIDGES"));
            put("put pistol cartridges", gameItems.get("PISTOL_CARTRIDGES"));
            put("put cartridges", gameItems.get("PISTOL_CARTRIDGES"));
            put("put rounds", gameItems.get("PISTOL_CARTRIDGES"));
            put("put shovel", gameItems.get("SHOVEL"));
            put("put harmonica", gameItems.get("HARMONICA"));
            put("put lighter", gameItems.get("LIGHTER"));
            put("put survival manual", gameItems.get("SURVIVAL_MANUAL"));
            put("put manual", gameItems.get("SURVIVAL_MANUAL"));
            put("put journal and pen", gameItems.get("JOURNAL"));
            put("put journal", gameItems.get("JOURNAL"));
            put("make bow", gameItems.get("BOW"));
        }};
    }

    public static void loadEncounters(){
        encounters = EncounterReader.readEncountersXML("./resources/xml/encounters.xml", gameItems);
        //add day encounters already coded to encounters hashmap.
        encounters.put("Bear", BearEncounterDay.getInstance());
        encounters.put("Helicopter", RescueHelicopterDay.getInstance());
    }

    public static HashMap<String, Item> getGameItems() {
        return gameItems;
    }
    public static HashMap<String, DayEncounter> getEncounters(){return encounters;}
}
