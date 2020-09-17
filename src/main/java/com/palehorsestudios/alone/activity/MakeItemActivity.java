package com.palehorsestudios.alone.activity;

import com.palehorsestudios.alone.Choice;

public class MakeItemActivity extends Activity {
    private static MakeItemActivity activityReference;

    private MakeItemActivity(){}

    public static Activity getInstance() {
        if(activityReference == null) {
            activityReference = new MakeItemActivity();
        }
        return activityReference;
    }

    @Override
    public String act(Choice choice) {
        String result = "You do not have the required resources to do that.";

        if (choice.getItem() != null) {
            choice.getPlayer().makeItem(choice.getItem().getName());
            result = "You made a " + choice.getItem().getVisibleName() + ".";
        }
        else if (choice.getFood() != null) {
            choice.getPlayer().makeItem(choice.getFood().getName());
            result = "You made some " + choice.getFood().getVisibleName() + ".";
        }

        return result;
    }

}
