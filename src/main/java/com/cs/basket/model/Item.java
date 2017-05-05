package com.cs.basket.model;

import com.cs.basket.exception.UnrecognisedItemException;

/**
 * NOTE - In real world, the prices would be loaded from an external source (e.g. RDBMS)
 */
public enum Item {
    APPLE("apple", 35),
    BANANA("banana", 20),
    MELON("melon", 50),
    LIME("lime", 15);

    private final String name;
    private final long priceInPence;

    Item(String name, long priceInPence) {
        this.name = name;
        this.priceInPence = priceInPence;
    }

    public long getPriceInPence() {
        return priceInPence;
    }

    public String getName() {
        return name;
    }

    public static Item itemFor(String item) throws UnrecognisedItemException {
        if (item != null) {
            for (Item each : Item.values()) {
                if (each.getName().equalsIgnoreCase(item.trim())) {
                    return each;
                }
            }
        }

        throw new UnrecognisedItemException(item);
    }
}
