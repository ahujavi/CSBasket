package com.cs.basket.model;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

import static java.lang.String.format;

/**
 * Represents an Order.
 * Once created, the items and their respective quantities cannot be changed.
 */
public class Order {

    /**
     * total original price for all the items in this order (this is the original price excluding any discounts)
     */
    private final long totalOriginalPriceInPence;

    /**
     * map of all the items and their quantity
     */
    private final ImmutableMap<Item, Long> items;

    /**
     * Creates an Order with the supplied items.
     * @param items the items.
     */
    public Order(Map<Item, Long> items, long totalOriginalPriceInPence) {

        if (items == null) {
            throw new IllegalArgumentException("items must not be null.");
        }

        this.items = ImmutableMap.copyOf(items);
        this.totalOriginalPriceInPence = totalOriginalPriceInPence;
    }

    public long getItemQuantity(Item item) {
        return this.items.containsKey(item) ? this.items.get(item) : 0;
    }

    public boolean hasItem(Item item) {
        return this.items.containsKey(item);
    }

    public long getTotalOriginalPriceInPence() {
        return totalOriginalPriceInPence;
    }

    /**
     * @return an ImmutableMap of the items contained in this order.
     */
    public ImmutableMap<Item, Long> getItems() {
        return items;
    }
}
