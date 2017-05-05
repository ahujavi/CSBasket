package com.cs.basket.service;

import com.cs.basket.exception.UnrecognisedItemException;

import java.util.List;

/**
 * Created by ahuja on 5/5/2017.
 */
public interface BasketService {
    /**
     * @param items the items to be priced. The names must match the names as defined in enum Items.java
     * @return the total price (in pence) of a list of items
     * @throws UnrecognisedItemException if it cannot recognise an item.
     */
    long priceBasket(List<String> items) throws UnrecognisedItemException;
}
