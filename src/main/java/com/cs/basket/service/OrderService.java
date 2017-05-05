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
}
