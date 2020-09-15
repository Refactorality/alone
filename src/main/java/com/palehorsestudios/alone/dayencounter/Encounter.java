package com.palehorsestudios.alone.dayencounter;

import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.GameAssets;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.player.Player;

import java.util.ArrayList;
import java.util.Map;

public class Encounter extends DayEncounter{
  private String name;
  private ArrayList<Item> protectiveItem = new ArrayList<>();
  private int weightChangeGood;
  private int moraleChangeGood;
  private int hydrationChangeGood;
  private int shelterChangeGood;
  private int weightChangeBad;
  private int moraleChangeBad;
  private int hydrationChangeBad;
  private int shelterChangeBad;
  private String responseGood;
  private String responseBad;
  private boolean needsFire;

  public Encounter(){};

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ArrayList<Item> getProtectiveItem() {
    return protectiveItem;
  }

  public void setProtectiveItem(String protectiveItem) {
    //if fire is the protective item, set needsFire to true and leave protective item null;
    if("fire".equalsIgnoreCase(protectiveItem)){
      this.needsFire = true;
    }else{
      this.protectiveItem.add(GameAssets.getGameItems().get(protectiveItem));
    }
  }

  public int getWeightChangeGood() {
    return weightChangeGood;
  }

  public void setWeightChangeGood(int weightChangeGood) {
    this.weightChangeGood = weightChangeGood;
  }

  public int getMoraleChangeGood() {
    return moraleChangeGood;
  }

  public void setMoraleChangeGood(int moraleChangeGood) {
    this.moraleChangeGood = moraleChangeGood;
  }

  public int getHydrationChangeGood() {
    return hydrationChangeGood;
  }

  public void setHydrationChangeGood(int hydrationChangeGood) {
    this.hydrationChangeGood = hydrationChangeGood;
  }

  public int getShelterChangeGood() {
    return shelterChangeGood;
  }

  public void setShelterChangeGood(int shelterChangeGood) {
    this.shelterChangeGood = shelterChangeGood;
  }

  public int getWeightChangeBad() {
    return weightChangeBad;
  }

  public void setWeightChangeBad(int weightChangeBad) {
    this.weightChangeBad = weightChangeBad;
  }

  public int getMoraleChangeBad() {
    return moraleChangeBad;
  }

  public void setMoraleChangeBad(int moraleChangeBad) {
    this.moraleChangeBad = moraleChangeBad;
  }

  public int getHydrationChangeBad() {
    return hydrationChangeBad;
  }

  public void setHydrationChangeBad(int hydrationChangeBad) {
    this.hydrationChangeBad = hydrationChangeBad;
  }

  public int getShelterChangeBad() {
    return shelterChangeBad;
  }

  public void setShelterChangeBad(int shelterChangeBad) {
    this.shelterChangeBad = shelterChangeBad;
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

  private String removeFoodFromShelter(Player player){
    Map<Food, Double> foodCache = player.getShelter().getFoodCache();
    if(foodCache.size() > 0){
      Food item = (Food)foodCache.keySet().toArray()[0];
      player.getShelter().removeFoodFromCache(item, 500.0);
      return item.getFoodName();
    }
    return null;
  }

  private boolean playerHasItem(Player player){
    boolean itemFound = false;
    for(Item item : protectiveItem){
      if(player.getShelter().getEquipment().containsKey(item)){
        itemFound = true;
      }
    }
    return itemFound;
  }

  private void updatePlayerStats(Player player, boolean success){
    if(success){
      player.updateWeight(weightChangeGood);
      player.updateMorale(moraleChangeGood);
      player.updateHydration(hydrationChangeGood);
      player.getShelter().setIntegrity(player.getShelter().getIntegrity() + shelterChangeGood);
    }else{
      player.updateWeight(weightChangeBad);
      player.updateMorale(getMoraleChangeBad());
      player.updateHydration(hydrationChangeBad);
      player.getShelter().setIntegrity(player.getShelter().getIntegrity() + shelterChangeBad);
    }
  }



  @Override
  public String encounter(Player player) {
    // if the player needs a fire for a successful encounter, check that. Otherwise, check for a protective item
    boolean successfulEncounter = needsFire ? player.getShelter().hasFire() : playerHasItem(player);
    //behavior for rain event
    if(name.toLowerCase().contains("rain")){
      player.getShelter().updateWater(2);
      if(player.getShelter().getWaterTank() == 0){
        moraleChangeBad = 0;
      }
    }
    //update player stats based on encounter outcome
    updatePlayerStats(player, successfulEncounter);

    //return response
    if(successfulEncounter){
      return responseGood;
    }
    else{
      // if it's an animal attack
      if(name.toLowerCase().contains("attack")){
        //have animal attacks remove food from cache
        String foodName = removeFoodFromShelter(player);
        if(foodName != null){
          return responseBad + " They got away with some " + foodName + "!";
        }else{
          return responseBad + " At least there was no food for them to steal.";
        }

      }else{
        return responseBad;
      }
    }
  }
}
