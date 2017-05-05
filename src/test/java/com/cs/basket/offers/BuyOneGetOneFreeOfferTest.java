package com.cs.basket.offers;

import com.cs.basket.model.Item;
import com.cs.basket.model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by ahuja on 3/27/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class BuyOneGetOneFreeOfferTest {
    BuyOneGetOneFreeOffer offer = new BuyOneGetOneFreeOffer();

    @Mock
    Order mockOrder;

    @Test
    public void calculateDiscountForAnQualifyingOrderWithSufficientQuantity() throws Exception {
        //given
        when(mockOrder.hasItem(Item.MELON)).thenReturn(true);

        when(mockOrder.getItemQuantity(Item.MELON)).thenReturn(2L);
        assertThat(offer.calculateDiscount(mockOrder), equalTo(Item.MELON.getPriceInPence()));

        when(mockOrder.getItemQuantity(Item.MELON)).thenReturn(3L);
        assertThat(offer.calculateDiscount(mockOrder), equalTo(Item.MELON.getPriceInPence()));

        when(mockOrder.getItemQuantity(Item.MELON)).thenReturn(4L);
        assertThat(offer.calculateDiscount(mockOrder), equalTo(2 * Item.MELON.getPriceInPence()));
    }

    @Test
    public void calculateDiscountForAnQualifyingOrderWithInsufficientQuantity() throws Exception {
        //given
        when(mockOrder.hasItem(Item.MELON)).thenReturn(true);
        when(mockOrder.getItemQuantity(Item.MELON)).thenReturn(1L);

        //then
        assertThat(offer.calculateDiscount(mockOrder), equalTo(0L));
    }

    @Test
    public void calculateDiscountForAnOrderWhichDoesNotQualify() throws Exception {
        //given
        when(mockOrder.hasItem(Item.MELON)).thenReturn(false);

        //then
        assertThat(offer.calculateDiscount(mockOrder), equalTo(0L));
    }

    @Test
    public void testOfferAppliesTo() throws Exception {
        //given
        when(mockOrder.hasItem(Item.MELON)).thenReturn(true);

        //then
        assertThat(offer.appliesTo(mockOrder), equalTo(true));

        //given
        when(mockOrder.hasItem(Item.MELON)).thenReturn(false);

        //then
        assertThat(offer.appliesTo(mockOrder), equalTo(false));
    }
}