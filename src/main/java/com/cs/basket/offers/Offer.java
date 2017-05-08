package com.cs.basket.offers;

import com.cs.basket.model.Item;
import com.cs.basket.model.Order;

import java.util.Arrays;
import java.util.function.BiFunction;

/**
 * Created by ahuja on 5/5/2017.
 */
public class Offer {
    //NOTE - in real world, the list of participating items will be loaded from a dynamic store (e.g. RDBMS).
    final Item[] participatingItems;

    final BiFunction<Long, Long, Long> offerFunction;

    public Offer(Item[] participatingItems, BiFunction<Long, Long, Long> offerFunction) {
        this.participatingItems = participatingItems;
        this.offerFunction = offerFunction;
    }

    /**
     * @param order the order on which to apply the offerFunction.
     * @return the discount if the supplied order qualifies for this offerFunction. Else 0
     */
    public long calculateDiscount(Order order) {
        return Arrays.stream(participatingItems)
                .filter(itemOnOffer -> order.hasItem(itemOnOffer))
                .mapToLong(itemOnOffer ->
                        offerFunction.apply(order.getItemQuantity(itemOnOffer), itemOnOffer.getPriceInPence())).sum();
    }

    /**
     * @param order the order to check against.
     * @return true if the supplied order qualifies for this offerFunction.
     */
    public boolean appliesTo(Order order) {
        for (Item itemOnOffer : participatingItems) {
            if (order.hasItem(itemOnOffer)) {
                return true;
            }
        }

        return false;
    }
}
