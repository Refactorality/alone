package com.palehorsestudios.alone.gui;

import com.palehorsestudios.alone.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import java.util.Set;

public class itemSelectionController {
  private Set<Item> initItems;
  @FXML private Button next;
  @FXML private Label countdown;
  @FXML private CheckBox fishingLine;
  @FXML private CheckBox fishingHooks;
  @FXML private CheckBox fishingLures;
  @FXML private CheckBox knife;
  @FXML private CheckBox flintandsteel;
  @FXML private CheckBox bow;
  @FXML private CheckBox arrows;
  @FXML private CheckBox familyPhoto;
  @FXML private CheckBox cordage;
  @FXML private CheckBox flare;
  @FXML private CheckBox extraBoots;
  @FXML private CheckBox extraPants;
  @FXML private CheckBox sleepingGear;
  @FXML private CheckBox coldWeatherGear;
  @FXML private CheckBox footTarp;
  @FXML private CheckBox matches;
  @FXML private CheckBox firstAid;
  @FXML private CheckBox flashlight;
  @FXML private CheckBox extraBatteries;
  @FXML private CheckBox gaugeWire;
  @FXML private CheckBox cookingPot;
  @FXML private CheckBox axe;
  @FXML private CheckBox hatchet;
  @FXML private CheckBox lodineTablets;
  @FXML private CheckBox magnumRevolver;
  @FXML private CheckBox cartridges;
  @FXML private CheckBox shovel;
  @FXML private CheckBox harmonica;
  @FXML private CheckBox lighter;
  @FXML private CheckBox survivalManual;
  @FXML private CheckBox journalandpen;

  public void selectItems() {
    if (fishingLine.isSelected()) {
      initItems.add(Item.FISHING_LINE);
    }
    if (fishingHooks)
  }
  public Button getNext() {
    return next;
  }

  public Label getCountdown() {
    return countdown;
  }

  public CheckBox getFishingLine() {
    return fishingLine;
  }

  public CheckBox getFishingHooks() {
    return fishingHooks;
  }

  public CheckBox getFishingLures() {
    return fishingLures;
  }

  public CheckBox getKnife() {
    return knife;
  }

  public CheckBox getFlintandsteel() {
    return flintandsteel;
  }

  public CheckBox getBow() {
    return bow;
  }

  public CheckBox getArrows() {
    return arrows;
  }

  public CheckBox getFamilyPhoto() {
    return familyPhoto;
  }

  public CheckBox getCordage() {
    return cordage;
  }

  public CheckBox getFlare() {
    return flare;
  }

  public CheckBox getExtraBoots() {
    return extraBoots;
  }

  public CheckBox getExtraPants() {
    return extraPants;
  }

  public CheckBox getSleepingGear() {
    return sleepingGear;
  }

  public CheckBox getColdWeatherGear() {
    return coldWeatherGear;
  }

  public CheckBox getFootTarp() {
    return footTarp;
  }

  public CheckBox getMatches() {
    return matches;
  }

  public CheckBox getFirstAid() {
    return firstAid;
  }

  public CheckBox getFlashlight() {
    return flashlight;
  }

  public CheckBox getExtraBatteries() {
    return extraBatteries;
  }

  public CheckBox getGaugeWire() {
    return gaugeWire;
  }

  public CheckBox getCookingPot() {
    return cookingPot;
  }

  public CheckBox getAxe() {
    return axe;
  }

  public CheckBox getHatchet() {
    return hatchet;
  }

  public CheckBox getLodineTablets() {
    return lodineTablets;
  }

  public CheckBox getMagnumRevolver() {
    return magnumRevolver;
  }

  public CheckBox getCartridges() {
    return cartridges;
  }

  public CheckBox getShovel() {
    return shovel;
  }

  public CheckBox getHarmonica() {
    return harmonica;
  }

  public CheckBox getLighter() {
    return lighter;
  }

  public CheckBox getSurvivalManual() {
    return survivalManual;
  }

  public CheckBox getJournalandpen() {
    return journalandpen;
  }
}
