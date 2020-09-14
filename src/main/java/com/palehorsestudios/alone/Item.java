package com.palehorsestudios.alone;

public class Item {
  private String itemName;

  public Item() {

  }

  public Item(String itemName) {
    this.itemName = itemName;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  @Override
  public String toString() {
    return this.itemName;
  }
}
