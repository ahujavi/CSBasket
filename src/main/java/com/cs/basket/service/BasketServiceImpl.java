package com.cs.basket.service;

import com.cs.basket.exception.UnrecognisedItemException;
import com.cs.basket.model.Order;
import com.cs.basket.offers.Offer;
import com.cs.basket.service.BasketService;
import com.cs.basket.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by ahuja on 5/5/2017.
 */
@Component
public class BasketServiceImpl implements BasketService {

    @Autowired
    OrderService orderService;

    @Override
    public long priceBasket(List<String> items) throws UnrecognisedItemException {
        //NOTE - encapsulating the order creation logic in a separate service allows us to keep Order as a POJO.
        Order order = orderService.createOrder(items);

        //NOTE - in real world, offers are expected to change very frequently and hence its best
        //to encapsulate it into a separate service.
        List<Offer> offers = orderService.getOffers(order);

        for(Offer offer : offers) {
            order.addDiscount(offer.calculateDiscount(order));
        }

        return order.getTotalPriceInPence();
    }
}
