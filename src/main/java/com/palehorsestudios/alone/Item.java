package com.palehorsestudios.alone;

public enum Item {
  FISHING_LINE("fishing line"),
  FISHING_HOOKS("fishing hooks"),
  FISHING_LURES("fishing lures"),
  KNIFE("knife"),
  FLINT_AND_STEEL("flint and steel"),
  BOW("bow"),
  ARROWS("arrows"),
  FAMILY_PHOTO("family photo"),
  PARACHUTE_CHORD("parachute chord"),
  FLARE("flare"),
  EXTRA_BOOTS("extra boots"),
  EXTRA_PANTS("extra pants"),
  SLEEPING_GEAR("sleeping gear"),
  COLD_WEATHER_GEAR("cold weather gear"),
  TARP("tarp"),
  MATCHES("matches"),
  FIRST_AID_KIT("first aid kit"),
  FLASHLIGHT("flashlight"),
  BATTERIES("batteries"),
  WIRE("18 gauge wire"),
  WATERPROOF_JACKET("waterproof jacket"),
  POT("cooking pot"),
  AXE("axe"),
  HATCHET("hatchet"),
  IODINE_TABLETS("iodine tablets"),
  PISTOL("pistol"),
  PISTOL_CARTRIDGES("12 pistol cartridges"),
  SHOVEL("shovel"),
  HARMONICA("harmonica"),
  LIGHTER("lighter"),
  SURVIVAL_MANUAL("survival manual"),
  JOURNAL("journal and pen");

  private final String str;

  private Item(String str) {
    this.str = str;
  }

  @Override
  public String toString() {
    return this.str;
  }
}
