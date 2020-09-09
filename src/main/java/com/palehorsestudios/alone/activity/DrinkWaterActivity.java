package com.palehorsestudios.alone.activity;

import com.palehorsestudios.alone.Choice;

public class DrinkWaterActivity extends Activity{
  private static DrinkWaterActivity activityReference;

  private DrinkWaterActivity(){}

  public static Activity getInstance() {
    if(activityReference == null) {
      activityReference = new DrinkWaterActivity();
    }
    return activityReference;
  }

  @Override
  public String act(Choice choice) {
    String result;
    int waterLevel = choice.getPlayer().getShelter().getWaterTank();
    if (waterLevel > 0) {
      if(waterLevel > 2) {
        choice.getPlayer().getShelter().updateWater(-3);
        choice.getPlayer().setHydration(choice.getPlayer().getHydration() + 3);
      } else if (waterLevel > 1) {
        choice.getPlayer().getShelter().updateWater(-2);
        choice.getPlayer().setHydration(choice.getPlayer().getHydration() + 2);
      } else {
        choice.getPlayer().getShelter().updateWater(-1);
        choice.getPlayer().setHydration(choice.getPlayer().getHydration() + 1);
      }
      result = "That's better. Your hydration is now at "
          + choice.getPlayer().getHydration()
          + ", and you have "
          + choice.getPlayer().getShelter().getWaterTank()
          + " water(s) remaining.";
    } else {
      result = "There isn't a drop left in your water tank. You should go fetch some water soon before you die of thirst!";
    }
    return result;
  }
}
