package com.palehorsestudios.alone.dayencounter;

import com.palehorsestudios.alone.GameAssets;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.player.Player;

public class WeatherEncounter extends DayEncounter{
  private String name;
  private Item protectiveItem;
  private int weightChange;
  private int moraleChange;
  private int hydrationChange;
  private String responseGood;
  private String responseBad;

  public WeatherEncounter(String itemName, int weightChange, int moraleChange, int hydrationChange, String responseGood, String responseBad){
    this.protectiveItem = GameAssets.gameItems.get(itemName);
    this.weightChange = weightChange;
    this.moraleChange = moraleChange;
    this.hydrationChange = hydrationChange;
    this.responseGood = responseGood;
    this.responseBad = responseBad;
  }

  public WeatherEncounter(){};

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Item getProtectiveItem() {
    return protectiveItem;
  }

  public void setProtectiveItem(Item protectiveItem) {
    this.protectiveItem = protectiveItem;
  }

  public int getWeightChange() {
    return weightChange;
  }

  public void setWeightChange(int weightChange) {
    this.weightChange = weightChange;
  }

  public int getMoraleChange() {
    return moraleChange;
  }

  public void setMoraleChange(int moraleChange) {
    this.moraleChange = moraleChange;
  }

  public int getHydrationChange() {
    return hydrationChange;
  }

  public void setHydrationChange(int hydrationChange) {
    this.hydrationChange = hydrationChange;
  }

  public String getResponseGood() {
    return responseGood;
  }

  public void setResponseGood(String responseGood) {
    this.responseGood = responseGood;
  }

  public String getResponseBad() {
    return responseBad;
  }

  public void setResponseBad(String responseBad) {
    this.responseBad = responseBad;
  }

  @Override
  public String encounter(Player player) {
    if(player.getShelter().getEquipment().containsKey(protectiveItem)){
      player.updateWeight(+weightChange);
      player.updateMorale(+moraleChange);
      player.updateHydration(+hydrationChange);
      return responseGood;
    }
    else{
      player.updateWeight(-weightChange);
      player.updateMorale(-moraleChange);
      player.updateHydration(-hydrationChange);
      return responseBad;
    }
  }
}
