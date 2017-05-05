package com.cs.basket.offers;

import com.cs.basket.model.Order;

import java.math.BigDecimal;

/**
 * Created by ahuja on 5/5/2017.
 */
public interface Offer {
    /**
     * @param order the order on which to apply the offer.
     * @return the discount if the supplied order qualifies for this offer. Else 0
     */
    long calculateDiscount(Order order);

    /**
     * @param order the order to check against.
     * @return true if the supplied order qualifies for this offer.
     */
    boolean appliesTo(Order order);
}
