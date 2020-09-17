package com.palehorsestudios.alone;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Item {
  private String name;
  private String visibleName;
  private List<Item> resourcesRequired;

  public Item() {
    resourcesRequired = new ArrayList<>();
  }

  public Item(String visibleName) {
    this.visibleName = visibleName;
  }

  public void addResourceRequired(Item item) {
    resourcesRequired.add(item);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getVisibleName() {
    return visibleName;
  }

  public void setVisibleName(String visibleName) {
    this.visibleName = visibleName;
  }

  public List<Item> getResourcesRequired() {
    return resourcesRequired;
  }

  public void setResourcesRequired(List<Item> resourcesRequired) {
    this.resourcesRequired = resourcesRequired;
  }

  @Override
  public String toString() {
    return this.visibleName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Item item = (Item) o;
    return Objects.equals(name, item.name) &&
            Objects.equals(visibleName, item.visibleName) &&
            Objects.equals(resourcesRequired, item.resourcesRequired);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, visibleName, resourcesRequired);
  }
}
