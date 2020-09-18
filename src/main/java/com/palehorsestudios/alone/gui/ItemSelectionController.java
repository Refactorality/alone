package com.palehorsestudios.alone.gui;

import com.palehorsestudios.alone.GameAssets;
import com.palehorsestudios.alone.Item;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ItemSelectionController {
  @FXML private GridPane paneSelected;
  @FXML private Button next;
  @FXML private Label countdown;
  @FXML private Label itemsSelectedCount;
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
  @FXML private Menu MenuTopTen;

  private static int count = 0;

  public Set<Item> selectItems() {
    Map<CheckBox, Item> inventory = new HashMap<>();
    inventory.put(fishingLine, GameAssets.gameItems.get("FISHING_LINE"));
    inventory.put(fishingHooks, GameAssets.gameItems.get("FISHING_HOOKS"));
    inventory.put(fishingLures, GameAssets.gameItems.get("FISHING_LURES"));
    inventory.put(knife, GameAssets.gameItems.get("KNIFE"));
    inventory.put(flintandsteel, GameAssets.gameItems.get("FLINT_AND_STEEL"));
    inventory.put(bow, GameAssets.gameItems.get("BOW"));
    inventory.put(arrows, GameAssets.gameItems.get("ARROWS"));
    inventory.put(familyPhoto, GameAssets.gameItems.get("FAMILY_PHOTO"));
    inventory.put(parachuteChord, GameAssets.gameItems.get("PARACHUTE_CHORD"));
    inventory.put(flare, GameAssets.gameItems.get("FLARE"));
    inventory.put(extraBoots, GameAssets.gameItems.get("EXTRA_BOOTS"));
    inventory.put(extraPants, GameAssets.gameItems.get("EXTRA_PANTS"));
    inventory.put(sleepingGear, GameAssets.gameItems.get("SLEEPING_GEAR"));
    inventory.put(coldWeatherGear, GameAssets.gameItems.get("COLD_WEATHER_GEAR"));
    inventory.put(footTarp, GameAssets.gameItems.get("TARP"));
    inventory.put(matches, GameAssets.gameItems.get("MATCHES"));
    inventory.put(firstAid, GameAssets.gameItems.get("FIRST_AID_KIT"));
    inventory.put(flashlight, GameAssets.gameItems.get("FLASHLIGHT"));
    inventory.put(extraBatteries, GameAssets.gameItems.get("BATTERIES"));
    inventory.put(gaugeWire, GameAssets.gameItems.get("WIRE"));
    inventory.put(cookingPot, GameAssets.gameItems.get("POT"));
    inventory.put(axe, GameAssets.gameItems.get("AXE"));
    inventory.put(hatchet, GameAssets.gameItems.get("HATCHET"));
    inventory.put(iodineTablets, GameAssets.gameItems.get("IODINE_TABLETS"));
    inventory.put(magnumRevolver, GameAssets.gameItems.get("PISTOL"));
    inventory.put(cartridges, GameAssets.gameItems.get("PISTOL_CARTRIDGES"));
    inventory.put(shovel, GameAssets.gameItems.get("SHOVEL"));
    inventory.put(harmonica, GameAssets.gameItems.get("HARMONICA"));
    inventory.put(lighter, GameAssets.gameItems.get("LIGHTER"));
    inventory.put(survivalManual, GameAssets.gameItems.get("SURVIVAL_MANUAL"));
    inventory.put(journalandpen, GameAssets.gameItems.get("JOURNAL"));

    Set<Item> initItems = new HashSet<>();

    for (Map.Entry<CheckBox, Item> entry : inventory.entrySet()) {
      entry
          .getKey()
          .selectedProperty()
          .addListener(
              new ChangeListener<Boolean>() {
                public void changed(ObservableValue ov, Boolean old_val, Boolean new_val) {
                  //if user selected 10 items, un-select their selection
                  if (count == 10 && entry.getKey().isSelected()) {
                    entry.getKey().setSelected(false);
                  } else {
                    if (entry.getKey().isSelected()) {
                      initItems.add(entry.getValue());
                      count++;
                    } else if (!entry.getKey().isSelected()
                        && initItems.contains(entry.getValue())) {
                      count--;
                      initItems.remove(entry.getValue());
                    }
                    //change display of gui item count to mirror this.count value every time this.count changes
                    itemsSelectedCount.setText(String.valueOf(count));
                  }
                }
              });
    }

    if (fishingLine.isSelected()) {
      initItems.add(GameAssets.gameItems.get("FISHING_LINE"));
    }
    if (fishingHooks.isSelected()) {
      initItems.add(GameAssets.gameItems.get("FISHING_HOOKS"));
    }
    if (fishingLures.isSelected()) {
      initItems.add(GameAssets.gameItems.get("FISHING_LURES"));
    }
    if (knife.isSelected()) {
      initItems.add(GameAssets.gameItems.get("KNIFE"));
    }
    if (flintandsteel.isSelected()) {
      initItems.add(GameAssets.gameItems.get("FLINT_AND_STEEL"));
    }
    if (bow.isSelected()) {
      initItems.add(GameAssets.gameItems.get("BOW"));
    }
    if (arrows.isSelected()) {
      initItems.add(GameAssets.gameItems.get("ARROWS"));
    }
    if (familyPhoto.isSelected()) {
      initItems.add(GameAssets.gameItems.get("FAMILY_PHOTO"));
    }
    if (parachuteChord.isSelected()) {
      initItems.add(GameAssets.gameItems.get("PARACHUTE_CHORD"));
    }
    if (flare.isSelected()) {
      initItems.add(GameAssets.gameItems.get("FLARE"));
    }
    if (extraBoots.isSelected()) {
      initItems.add(GameAssets.gameItems.get("EXTRA_BOOTS"));
    }
    if (extraPants.isSelected()) {
      initItems.add(GameAssets.gameItems.get("EXTRA_PANTS"));
    }
    if (sleepingGear.isSelected()) {
      initItems.add(GameAssets.gameItems.get("SLEEPING_GEAR"));
    }
    if (coldWeatherGear.isSelected()) {
      initItems.add(GameAssets.gameItems.get("COLD_WEATHER_GEAR"));
    }
    if (footTarp.isSelected()) {
      initItems.add(GameAssets.gameItems.get("TARP"));
    }
    if (matches.isSelected()) {
      initItems.add(GameAssets.gameItems.get("MATCHES"));
    }
    if (firstAid.isSelected()) {
      initItems.add(GameAssets.gameItems.get("FIRST_AID_KIT"));
    }
    if (flashlight.isSelected()) {
      initItems.add(GameAssets.gameItems.get("FLASHLIGHT"));
    }
    if (extraBatteries.isSelected()) {
      initItems.add(GameAssets.gameItems.get("BATTERIES"));
    }
    if (gaugeWire.isSelected()) {
      initItems.add(GameAssets.gameItems.get("WIRE"));
    }
    if (cookingPot.isSelected()) {
      initItems.add(GameAssets.gameItems.get("POT"));
    }
    if (axe.isSelected()) {
      initItems.add(GameAssets.gameItems.get("AXE"));
    }
    if (hatchet.isSelected()) {
      initItems.add(GameAssets.gameItems.get("HATCHET"));
    }
    if (iodineTablets.isSelected()) {
      initItems.add(GameAssets.gameItems.get("IODINE_TABLETS"));
    }
    if (magnumRevolver.isSelected()) {
      initItems.add(GameAssets.gameItems.get("PISTOL"));
    }
    if (cartridges.isSelected()) {
      initItems.add(GameAssets.gameItems.get("PISTOL_CARTRIDGES"));
    }
    if (shovel.isSelected()) {
      initItems.add(GameAssets.gameItems.get("SHOVEL"));
    }

    if (harmonica.isSelected()) {
      initItems.add(GameAssets.gameItems.get("HARMONICA"));
    }
    if (lighter.isSelected()) {
      initItems.add(GameAssets.gameItems.get("LIGHTER"));
    }
    if (survivalManual.isSelected()) {
      initItems.add(GameAssets.gameItems.get("SURVIVAL_MANUAL"));
    }
    if (journalandpen.isSelected()) {
      initItems.add(GameAssets.gameItems.get("JOURNAL"));
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

  public Menu getMenuTopTen() {
    return MenuTopTen;
  }

  public void setMenuTopTen(Menu menuTopTen) {
    MenuTopTen = menuTopTen;
  }
}
