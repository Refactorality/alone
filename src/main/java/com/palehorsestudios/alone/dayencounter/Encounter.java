package com.palehorsestudios.alone.dayencounter;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.GameAssets;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.player.Player;
import com.palehorsestudios.alone.util.Sound;

import java.util.ArrayList;
import java.util.Map;

public class Encounter extends DayEncounter{
//  private String name;
  private final ArrayList<Item> protectiveItem = new ArrayList<>();
  private Item itemUsed;
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
  private boolean needsStrongShelter;
  private int thiefCount;

  public Encounter(){};

  public ArrayList<Item> getProtectiveItem() {
    return protectiveItem;
  }

  public void setProtectiveItem(String protectiveItem) {
    //if fire is the protective item, set needsFire to true and leave protective item null;
    if("fire".equalsIgnoreCase(protectiveItem)){
      this.needsFire = true;
    }else if("shelter".equalsIgnoreCase(protectiveItem)){
      this.needsStrongShelter = true;
    }
    else{
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
      Food item = (Food)foodCache.keySet().toArray()[foodCache.size()-1];
      player.getShelter().removeFoodFromCache(item, item.getGrams()*0.4);
      return item.getVisibleName();
    }
    return null;
  }

  private boolean playerHasItem(Player player){
    boolean itemFound = false;
    for(Item item : protectiveItem){
      if (player.getShelter().getEquipment().containsKey(item)) {
        itemFound = true;
        //fox thief check for extra boots
        if(item.getName().equalsIgnoreCase("EXTRA_BOOTS")
        && player.getShelter().getEquipment().get(item) < 1){
          itemFound = false;
        }
        itemUsed = item;
        break;
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


  //rain fills up tank
  private void rainCheck(Player player){
    // if it is a rain behavior
    if(getName().toLowerCase().contains("rain")){
      // increase water
      player.getShelter().updateWater(1);
      // not sad if no water
      if(player.getShelter().getWaterTank() == 0){
        moraleChangeBad = 0;
      }
    }
  }
  //animals steal food
  private String animalCheck(Player player, String response){
    if(getName().toLowerCase().contains("attack")){
      //have animal attacks remove food from cache
      String foodName = removeFoodFromShelter(player);
      if(foodName != null){
        response += " They got away with some " + foodName + "!";
      }else{
        response += " At least there was no food for them to steal.";
      }
    }
    return response;
  }
  // functionality for fire
  private String fireCheck(Player player, String response) {
    if (getName().toLowerCase().contains("fire") && player.getShelter().getWaterTank() > 2) {
        player.getShelter().updateWater(-10);
        this.shelterChangeBad = 0;
        response = "There was a forest fire. With nothing to stop the spread, you attempt to douse it with your water. Your shelter is saved but you got burned.";
    }
    return response;
  }
  // function removes extra_boots from inventory for fox thief
  private void thiefCheck(Player player){
    if(getName().toLowerCase().contains("thief")) {
      if (player.getShelter().getEquipment().get(protectiveItem.get(0)) != null
              && player.getShelter().getEquipment().get(protectiveItem.get(0)) > 0) {
        player.getShelter().removeEquipment(protectiveItem.get(0), 1);
      }else{
        this.setName("Fox Attack");
      }
    }
  }
  //put out fire if rain or wind storm
  private void stormCheck(Player player){
    if(getName().toLowerCase().contains("storm")){
      player.getShelter().setFire(false);
    }
  }

  @Override
  public String encounter(Player player) {
    String response;
    // successful if player needs fire and has fire or needs strong shelter and has integrity > 6 or has item.
    boolean successfulEncounter = needsFire && player.getShelter().hasFire()
            || (needsStrongShelter && player.getShelter().getIntegrity() > 6
            || playerHasItem(player));

    //behavior for rain event
    rainCheck(player);
    //encounters with thief in name. currently foxes
    thiefCheck(player);
    //storms put out fire
    stormCheck(player);

    // update to successful response
    if(successfulEncounter){
      response = responseGood;
      //check for foxes stealing boots.
      // modify good response based on item
      response += " thanks to your " + (needsFire && player.getShelter().hasFire() ? "fire." :
              needsStrongShelter && player.getShelter().getIntegrity() > 6 ? "strong shelter." :
                      itemUsed.getVisibleName() + ".");
    }
    // update failure response
    else{
      // check for fire, check for animal, submit failure response
      response = fireCheck(player, animalCheck(player, responseBad));
    }
    //update player stats based on encounter outcome
    updatePlayerStats(player, successfulEncounter);
    // if died as a result add eulogy
    if(player.isDead()){
      response = response + " \nThey say we all die alone. You die alone as a result of a " + this.getName() + ".";
    }
    return response;
  }
}
