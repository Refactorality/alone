package com.palehorsestudios.alone.gui;

import com.palehorsestudios.alone.Item;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ItemSelectionController {
  @FXML private GridPane paneSelected;
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
  @FXML private CheckBox parachuteChord;
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
  @FXML private CheckBox iodineTablets;
  @FXML private CheckBox magnumRevolver;
  @FXML private CheckBox cartridges;
  @FXML private CheckBox shovel;
  @FXML private CheckBox harmonica;
  @FXML private CheckBox lighter;
  @FXML private CheckBox survivalManual;
  @FXML private CheckBox journalandpen;

  private static int count = 0;
  public Set<Item> selectItems() {
    Map<CheckBox, Item> inventory = new HashMap<>();
    inventory.put(fishingLine, Item.FISHING_LINE);
    inventory.put(fishingHooks, Item.FISHING_HOOKS);
    inventory.put(fishingLures, Item.FISHING_LURES);
    inventory.put(knife, Item.KNIFE);
    inventory.put(flintandsteel, Item.FLINT_AND_STEEL);
    inventory.put(bow, Item.BOW);
    inventory.put(arrows, Item.ARROWS);
    inventory.put(familyPhoto, Item.FAMILY_PHOTO);
    inventory.put(parachuteChord, Item.PARACHUTE_CHORD);
    inventory.put(flare, Item.FLARE);
    inventory.put(extraBoots, Item.EXTRA_BOOTS);
    inventory.put(extraPants, Item.EXTRA_PANTS);
    inventory.put(sleepingGear, Item.SLEEPING_GEAR);
    inventory.put(coldWeatherGear, Item.COLD_WEATHER_GEAR);
    inventory.put(footTarp, Item.TARP);
    inventory.put(matches, Item.MATCHES);
    inventory.put(firstAid, Item.FIRST_AID_KIT);
    inventory.put(flashlight, Item.FLASHLIGHT);
    inventory.put(extraBatteries, Item.BATTERIES);
    inventory.put(gaugeWire, Item.WIRE);
    inventory.put(cookingPot, Item.POT);
    inventory.put(axe, Item.AXE);
    inventory.put(hatchet, Item.HATCHET);
    inventory.put(iodineTablets, Item.IODINE_TABLETS);
    inventory.put(magnumRevolver, Item.PISTOL);
    inventory.put(cartridges, Item.PISTOL_CARTRIDGES);
    inventory.put(shovel, Item.SHOVEL);
    inventory.put(harmonica, Item.HARMONICA);
    inventory.put(lighter, Item.LIGHTER);
    inventory.put(survivalManual, Item.SURVIVAL_MANUAL);
    inventory.put(journalandpen, Item.JOURNAL);

    Set<Item> initItems = new HashSet<>();

    for (Map.Entry<CheckBox, Item> entry : inventory.entrySet()) {
      entry.getKey().selectedProperty().addListener(new ChangeListener<Boolean>() {
        public void changed(ObservableValue ov, Boolean old_val, Boolean new_val) {
          if(count == 9) {
            paneSelected.setDisable(true);
          }else {
            count++;
          }
          //initItems.add(entry.getValue()) TODO maybe get this to work to eliminate the lines below;
        }
      });
    }

    if (fishingLine.isSelected()) {
      initItems.add(Item.FISHING_LINE);
    }
    if (fishingHooks.isSelected()) {
      initItems.add(Item.FISHING_HOOKS);
    }
    if (fishingLures.isSelected()) {
      initItems.add(Item.FISHING_LURES);
    }
    if (knife.isSelected()) {
      initItems.add(Item.KNIFE);
    }
    if (flintandsteel.isSelected()) {
      initItems.add(Item.FLINT_AND_STEEL);
    }
    if (bow.isSelected()) {
      initItems.add(Item.BOW);
    }
    if (arrows.isSelected()) {
      initItems.add(Item.ARROWS);
    }
    if (familyPhoto.isSelected()) {
      initItems.add(Item.FAMILY_PHOTO);
    }
    if (parachuteChord.isSelected()) {
      initItems.add(Item.PARACHUTE_CHORD);
    }
    if (flare.isSelected()) {
      initItems.add(Item.FLARE);
    }
    if (extraBoots.isSelected()) {
      initItems.add(Item.EXTRA_BOOTS);
    }
    if (extraPants.isSelected()) {
      initItems.add(Item.EXTRA_PANTS);
    }
    if (sleepingGear.isSelected()) {
      initItems.add(Item.SLEEPING_GEAR);
    }
    if (coldWeatherGear.isSelected()) {
      initItems.add(Item.COLD_WEATHER_GEAR);
    }
    if (footTarp.isSelected()) {
      initItems.add(Item.TARP);
    }
    if (matches.isSelected()) {
      initItems.add(Item.MATCHES);
    }
    if (firstAid.isSelected()) {
      initItems.add(Item.FIRST_AID_KIT);
    }
    if (flashlight.isSelected()) {
      initItems.add(Item.FLASHLIGHT);
    }
    if (extraBatteries.isSelected()) {
      initItems.add(Item.BATTERIES);
    }
    if (gaugeWire.isSelected()) {
      initItems.add(Item.WIRE);
    }
    if (cookingPot.isSelected()) {
      initItems.add(Item.POT);
    }
    if (axe.isSelected()) {
      initItems.add(Item.AXE);
    }
    if (hatchet.isSelected()) {
      initItems.add(Item.HATCHET);
    }
    if (iodineTablets.isSelected()) {
      initItems.add(Item.IODINE_TABLETS);
    }
    if (magnumRevolver.isSelected()) {
      initItems.add(Item.PISTOL);
    }
    if (cartridges.isSelected()) {
      initItems.add(Item.PISTOL_CARTRIDGES);
    }
    if (shovel.isSelected()) {
      initItems.add(Item.SHOVEL);
    }

    if (harmonica.isSelected()) {
      initItems.add(Item.HARMONICA);
    }
    if (lighter.isSelected()) {
      initItems.add(Item.LIGHTER);
    }
    if (survivalManual.isSelected()) {
      initItems.add(Item.SURVIVAL_MANUAL);
    }
    if (journalandpen.isSelected()) {
      initItems.add(Item.JOURNAL);
    }


    return initItems;
  }

  public Button getNext() {
    return next;
  }

  public Label getCountdown() {
    return countdown;
  }

  public GridPane getPaneSelected() {
    return paneSelected;
  }

}
