package com.palehorsestudios.alone.activity;

import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.GameAssets;
import com.palehorsestudios.alone.Item;

import java.util.Map;
import java.util.Random;

public class GatherResourceActivity extends Activity {
    private static GatherResourceActivity activityReference;

    private GatherResourceActivity(){}

    public static Activity getInstance() {
        if(activityReference == null) {
            activityReference = new GatherResourceActivity();
        }
        activityReference.setActivityName("GatherResourceActivity");
        return activityReference;
    }

    @Override
    public String act(Choice choice) {
        String result = "You could not find any resources.";
        Random random = new Random();
        int randomNumber = random.nextInt(7);
        Map<Item, Integer> equipment = choice.getPlayer().getShelter().getEquipment();

        switch (randomNumber) {
            case 6:
                if (equipment.containsKey(GameAssets.gameItems.get("CLOTH"))) {
                    int newNumCloth = equipment.get(GameAssets.gameItems.get("CLOTH")) + 1;
                    equipment.replace(GameAssets.gameItems.get("CLOTH"), newNumCloth);
                }
                else {
                    equipment.put(GameAssets.gameItems.get("CLOTH"), 1);
                }
                result = "You found some cloth!";
                break;

            case 5:
                if (equipment.containsKey(GameAssets.gameItems.get("PLASTIC"))) {
                    int newNumPlastic = equipment.get(GameAssets.gameItems.get("PLASTIC")) + 1;
                    equipment.replace(GameAssets.gameItems.get("PLASTIC"), newNumPlastic);
                }
                else {
                    equipment.put(GameAssets.gameItems.get("PLASTIC"), 1);
                }
                result = "You found some plastic!";
                break;

            case 4:
                if (equipment.containsKey(GameAssets.gameItems.get("METAL"))) {
                    int newNumMetal = equipment.get(GameAssets.gameItems.get("METAL")) + 1;
                    equipment.replace(GameAssets.gameItems.get("METAL"), newNumMetal);
                }
                else {
                    equipment.put(GameAssets.gameItems.get("METAL"), 1);
                }
                result = "You found some metal!";
                break;

            case 3:
                if (equipment.containsKey(GameAssets.gameItems.get("WOOD"))) {
                    int newNumWood = equipment.get(GameAssets.gameItems.get("WOOD")) + 1;
                    equipment.replace(GameAssets.gameItems.get("WOOD"), newNumWood);
                }
                else {
                    equipment.put(GameAssets.gameItems.get("WOOD"), 1);
                }
                result = "You found some wood!";
                break;

            case 2:
                if (equipment.containsKey(GameAssets.gameItems.get("STONE"))) {
                    int newNumStone = equipment.get(GameAssets.gameItems.get("STONE")) + 1;
                    equipment.replace(GameAssets.gameItems.get("STONE"), newNumStone);
                }
                else {
                    equipment.put(GameAssets.gameItems.get("STONE"), 1);
                }
                result = "You found some stone!";
                break;

            case 1:
                if (equipment.containsKey(GameAssets.gameItems.get("STRING"))) {
                    int newNumString = equipment.get(GameAssets.gameItems.get("STRING")) + 1;
                    equipment.replace(GameAssets.gameItems.get("STRING"), newNumString);
                }
                else {
                    equipment.put(GameAssets.gameItems.get("STRING"), 1);
                }
                result = "You found some string!";
                break;
        }

        return result;
    }
}
