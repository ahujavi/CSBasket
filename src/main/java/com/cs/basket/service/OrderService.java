package com.cs.basket.service;

import com.cs.basket.exception.UnrecognisedItemException;
import com.cs.basket.model.Item;
import com.cs.basket.model.Order;
import com.cs.basket.offers.BuyOneGetOneFreeOffer;
import com.cs.basket.offers.Offer;
import com.cs.basket.offers.ThreeForTwoOffer;
import com.cs.basket.service.OrderService;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

import static com.cs.basket.model.Item.LIME;
import static com.cs.basket.model.Item.MELON;

/**
 * Created by ahuja on 5/5/2017.
 */
@Component
public class OrderServiceImpl implements OrderService {
    //NOTE - For now, I have assumed that "all" offers will be pre-loaded. However, we might need to provide a
    //mechanism to support the fact that offers are expected to change very frequently in the real world.
    @Autowired
    Collection<Offer> offers;

    void setOffers(Collection<Offer> offers) {
        this.offers = offers;
    }

    //NOTE - encapsulating the order creation logic in a separate service allows us to keep Order as a POJO.
    @Override
    public Order createOrder(List<String> items) throws UnrecognisedItemException {
        //prepare a map of Item and its quantity.
        Map<Item, Long> mapOfItems = Optional.ofNullable(items).orElse(new ArrayList<>()).stream()
                .map(i -> Item.itemFor(i))
                .collect(Collectors.groupingBy(v -> v, Collectors.counting()));

        //calculate total price prior to any discounts
        long totalOriginalPrice = mapOfItems.entrySet().stream().mapToLong(
                item -> item.getKey().getPriceInPence() * item.getValue()).sum();

        return new Order(mapOfItems, totalOriginalPrice);
    }

    /**
     * NOTE - For now, I have assumed that "all" offers will be pre-loaded. However, this method gives us the
     * opportunity to
     * A) load offers dynamically (e.g. from a RDBMS)
     * B) return only those offers which apply to the order
     *
     * @param order identifies the order.
     * @return the list of offers applicable to the order.
     */
    @Override
    public List<Offer> getOffers(final Order order) {
        return offers.stream().filter(offer -> offer.appliesTo(order)).collect(Collectors.toList());
    }

    @Override
    public long calculateDiscount(Order order, List<Offer> offers) {
        return offers.stream().mapToLong(offer -> offer.calculateDiscount(order)).sum();
    }

}
