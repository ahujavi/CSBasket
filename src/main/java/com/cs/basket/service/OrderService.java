package com.cs.basket.service;

import com.cs.basket.exception.UnrecognisedItemException;
import com.cs.basket.model.Order;
import com.cs.basket.offers.Offer;

import java.util.List;

/**
 * Created by ahuja on 5/5/2017.
 */
public interface OrderService {
    /**
     * @param items the items.
     * @return an order for the supplied items
     * @throws UnrecognisedItemException if any item is unrecognised.
     */
    Order createOrder(List<String> items) throws UnrecognisedItemException;

    /**
     * @param order identifies the order.
     * @return the list of offers applicable to the order.
     */
    List<Offer> getOffers(Order order);

    /**
     * @param order the order for which to calculate the discount
     * @param offers the discount is calculated on the basis of the offers.
     * @return the discount for the order based on the supplied offers.
     */
    long calculateDiscount(Order order, List<Offer> offers);
}
