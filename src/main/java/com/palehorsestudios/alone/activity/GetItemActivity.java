package com.palehorsestudios.alone.activity;

import com.palehorsestudios.alone.Choice;
import java.util.Optional;

public class GetItemActivity extends Activity{

  private static final int MAX_ITEM_CARRY_SIZE = 3;
  private static GetItemActivity activityReference;

    private GetItemActivity(){}

    public static Activity getInstance() {
      if(activityReference == null) {
        activityReference = new GetItemActivity();
      }
      return activityReference;
    }

    @Override
    public String act(Choice choice) {
      String result;
      /* determine if player has less than the maximum carry limit
      and item is in shelter. */
      Optional<Integer> shelterItemCount = Optional.ofNullable
          (choice.getPlayer().getShelter().getEquipment().remove(new Choice("remove", player, item)));
      if (shelterItemCount.isPresent() && shelterItemCount.get() > 0) {
        if(choice.getPlayer().getItems().size() < MAX_ITEM_CARRY_SIZE) {
          int retrievalResult = choice.getPlayer().getShelter().removeEquipment(item, 1);
          result = "You retrieved " + retrievalResult + " " + item + " from your shelter.";
          this.items.add(item);
        } else {
          result = "You can only carry " + MAX_ITEM_CARRY_SIZE + " items.";
        }
      } else {
        result = "You do not have a(n) " + item + " in your shelter.";
      }
      return result;
    }
}
