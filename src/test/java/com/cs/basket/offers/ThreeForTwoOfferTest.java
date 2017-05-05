package com.cs.basket.offers;

import com.cs.basket.model.Item;
import com.cs.basket.model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by ahuja on 3/27/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class ThreeForTwoOfferTest {
    ThreeForTwoOffer offer = new ThreeForTwoOffer();

    @Mock
    Order mockOrder;

    @Test
    public void calculateDiscountForAnQualifyingOrderWithSufficientQuantity() throws Exception {
        //given
        when(mockOrder.hasItem(Item.LIME)).thenReturn(true);

        when(mockOrder.getItemQuantity(Item.LIME)).thenReturn(3L);
        assertThat(offer.calculateDiscount(mockOrder), equalTo(Item.LIME.getPriceInPence()));

        when(mockOrder.getItemQuantity(Item.LIME)).thenReturn(4L);
        assertThat(offer.calculateDiscount(mockOrder), equalTo(Item.LIME.getPriceInPence()));

        when(mockOrder.getItemQuantity(Item.LIME)).thenReturn(6L);
        assertThat(offer.calculateDiscount(mockOrder), equalTo(2 * Item.LIME.getPriceInPence()));
    }

    @Test
    public void calculateDiscountForAnQualifyingOrderWithInsufficientQuantity() throws Exception {
        //given
        when(mockOrder.hasItem(Item.LIME)).thenReturn(true);

        when(mockOrder.getItemQuantity(Item.LIME)).thenReturn(1L);
        assertThat(offer.calculateDiscount(mockOrder), equalTo(0L));

        when(mockOrder.getItemQuantity(Item.LIME)).thenReturn(2L);
        assertThat(offer.calculateDiscount(mockOrder), equalTo(0L));
    }

    @Test
    public void calculateDiscountForAnOrderWhichDoesNotQualify() throws Exception {
        //given
        when(mockOrder.hasItem(Item.LIME)).thenReturn(false);

        //then
        assertThat(offer.calculateDiscount(mockOrder), equalTo(0L));
    }

    @Test
    public void testOfferAppliesTo() throws Exception {
        //given
        when(mockOrder.hasItem(Item.LIME)).thenReturn(true);

        //then
        assertThat(offer.appliesTo(mockOrder), equalTo(true));

        //given
        when(mockOrder.hasItem(Item.LIME)).thenReturn(false);

        //then
        assertThat(offer.appliesTo(mockOrder), equalTo(false));
    }
}