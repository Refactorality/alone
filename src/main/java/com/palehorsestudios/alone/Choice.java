package com.palehorsestudios.alone;

import com.google.common.base.Objects;
import com.palehorsestudios.alone.player.Player;

public class Choice {
  private String keyword;
  private Item item;
  private Food food;
  private Player player;

  public Choice(String keyword, Player player) {
    this.keyword = keyword;
    this.player = player;
  }

  public Choice(String keyword, Player player, Food food) {
    this(keyword, player);
    this.food = food;
  }

  public Choice(String keyword, Player player, Item item) {
    this(keyword, player);
    this.item = item;
  }

  public Player getPlayer() {
    return player;
  }

  public String getKeyword() {
    return keyword;
  }

  public Item getItem() {
    return item;
  }

  public Food getFood() {
    return food;
  }

  @Override
  public String toString() {
    return "Choice{" +
        "keyword='" + keyword + '\'' +
        ", item=" + item +
        ", food=" + food +
        ", player=" + player +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Choice choice = (Choice) o;
    return Objects.equal(keyword, choice.keyword) &&
        item == choice.item &&
        food == choice.food &&
        Objects.equal(player, choice.player);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(keyword, item, food, player);
  }
}
