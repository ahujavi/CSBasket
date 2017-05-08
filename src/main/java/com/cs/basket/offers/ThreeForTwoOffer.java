package com.cs.basket.offers;

import com.cs.basket.model.Item;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

import static com.cs.basket.model.Item.LIME;

/**
 * Created by ahuja on 5/5/2017.
 */
@Component
public class ThreeForTwoOffer extends Offer {
    static final BiFunction<Long, Long, Long> threeForTwoOffer = (itemQuantity, itemPrice) -> itemPrice * (itemQuantity/3);

    public ThreeForTwoOffer() {
        super(new Item[]{LIME}, threeForTwoOffer);
    }

}
