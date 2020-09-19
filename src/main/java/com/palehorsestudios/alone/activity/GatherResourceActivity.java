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
        int randomNumber = random.nextInt(4);
        Map<Item, Integer> equipment = choice.getPlayer().getShelter().getEquipment();

        switch (randomNumber) {
            case 3:
                if (equipment.containsKey(GameAssets.gameItems.get("WOOD"))) {
                    int oldNumWood = equipment.get(GameAssets.gameItems.get("WOOD")) + 1;
                    equipment.replace(GameAssets.gameItems.get("WOOD"), oldNumWood);
                }
                else {
                    equipment.put(GameAssets.gameItems.get("WOOD"), 1);
                }
                result = "You found some wood!";
                break;

            case 2:
                if (equipment.containsKey(GameAssets.gameItems.get("STONE"))) {
                    int oldNumStone = equipment.get(GameAssets.gameItems.get("STONE")) + 1;
                    equipment.replace(GameAssets.gameItems.get("STONE"), oldNumStone);
                }
                else {
                    equipment.put(GameAssets.gameItems.get("STONE"), 1);
                }
                result = "You found some stone!";
                break;

            case 1:
                if (equipment.containsKey(GameAssets.gameItems.get("STRING"))) {
                    int oldNumString = equipment.get(GameAssets.gameItems.get("STRING")) + 1;
                    equipment.replace(GameAssets.gameItems.get("STRING"), oldNumString);
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
