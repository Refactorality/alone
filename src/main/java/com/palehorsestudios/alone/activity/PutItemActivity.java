package com.palehorsestudios.alone.activity;

import com.palehorsestudios.alone.Choice;

public class PutItemActivity extends Activity{
  private static PutItemActivity activityReference;

  private PutItemActivity(){}

  public static Activity getInstance() {
    if(activityReference == null) {
      activityReference = new PutItemActivity();
    }
    return activityReference;
  }

  @Override
  public String act(Choice choice) {
    String result;
    if (choice.getPlayer().getItems().remove(choice.getItem())) {
      choice.getPlayer().getShelter().addEquipment(choice.getItem(), 1);
      result = "One " + choice.getItem() + " moved to your shelter.";
    } else {
      result = "You do not have a(n) " + choice.getItem() + " on you.";
    }
    return result;
  }
}
