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
    if (choice.getPlayer().getItems().remove(items))) {
      choice.getPlayer().getShelter().addEquipment(item, 1);
      result = "One " + item + " moved to your shelter.";
    } else {
      result = "You do not have a(n) " + item + " on you.";
    }
    return result;
  }
}
