package com.cs.basket.offers;

import com.cs.basket.model.Item;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

import static com.cs.basket.model.Item.MELON;

@Component
public class BuyOneGetOneFreeOffer extends Offer {

    static final BiFunction<Long, Long, Long> buyOneGetOneFree = (itemQuantity, itemPrice) -> itemPrice * (itemQuantity/2);

    public BuyOneGetOneFreeOffer() {
        super(new Item[]{MELON}, buyOneGetOneFree);
    }

}
