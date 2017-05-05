package com.cs.basket.offers;

import com.cs.basket.model.Item;
import com.cs.basket.model.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiFunction;

import static com.cs.basket.model.Item.LIME;

/**
 * Created by ahuja on 5/5/2017.
 */
@Component
public class ThreeForTwoOffer implements Offer {
    //NOTE - in real world, the list of participating items will be loaded from a dynamic store (e.g. RDBMS).
    final Item[] participatingItems = new Item[]{LIME};

    BiFunction<Long, Long, Long> threeForTwoOffer = (itemQuantity, itemPrice) -> itemPrice * (itemQuantity/3);

    @Override
    public long calculateDiscount(Order order) {
        LongAdder discount = new LongAdder();
        Arrays.stream(participatingItems)
                .filter(itemOnOffer -> order.hasItem(itemOnOffer))
                .forEach(itemOnOffer ->
                        discount.add(threeForTwoOffer.apply(order.getItemQuantity(itemOnOffer), itemOnOffer.getPriceInPence())));


        return discount.longValue();
    }

    @Override
    public boolean appliesTo(Order order) {
        for (Item itemOnOffer : participatingItems) {
            if (order.hasItem(itemOnOffer)) {
                return true;
            }
        }

        return false;
    }
}
