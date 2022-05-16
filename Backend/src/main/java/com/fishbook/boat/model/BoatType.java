package com.fishbook.boat.model;

public enum BoatType {
    JET_SKI("Jet Ski"),
    INFLATABLE_BOAT("Inflatable Boat"),
    AIRBOAT("Air Boat"),
    SPEEDBOAT("Speed Boat"),
    FERRY("Ferry"),
    SMALL_YACHT("Small Yacht"),
    BIG_YACHT("Bih Yacht"),
    SAILBOAT("Sail boat"),
    BRIG("Brig"),
    CABIN_CRUISER("Cabin Cruiser"),
    CRUISE("Cruise");

    private final String enumText;

    BoatType(String enumText) {
        this.enumText = enumText;
    }

    @Override
    public String toString() {
        return enumText;
    }
}
