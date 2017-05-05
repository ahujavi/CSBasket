package com.cs.basket.service;

import com.cs.basket.exception.UnrecognisedItemException;
import com.cs.basket.model.Item;
import com.cs.basket.model.Order;
import com.cs.basket.offers.Offer;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by ahuja on 3/26/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @Mock
    Order mockOrder;

    @Mock
    Offer mockOffer1;

    @Mock
    Offer mockOffer2;

    //@InjectMocks
    OrderServiceImpl orderService = new OrderServiceImpl();

    @Before
    public void setUp() throws Exception {
        orderService.setOffers(newArrayList(mockOffer1, mockOffer2));
    }

    @Test
    public void createOrderWithNoItems() throws Exception {
        //when
        Order order = orderService.createOrder(new ArrayList<String>());

        //then
        assertThat(order.getTotalOriginalPriceInPence(), equalTo(0L));
        assertThat(order.getTotalDiscountInPence(), equalTo(0L));
        assertThat(order.getTotalPriceInPence(), equalTo(0L));
        assertThat(order.getItems(), equalTo(ImmutableMap.<Item, Integer>of()));

        //when
        order = orderService.createOrder(null);

        //then
        assertThat(order.getTotalOriginalPriceInPence(), equalTo(0L));
        assertThat(order.getTotalDiscountInPence(), equalTo(0L));
        assertThat(order.getTotalPriceInPence(), equalTo(0L));
        assertThat(order.getItems(), equalTo(ImmutableMap.<Item, Integer>of()));
    }

    @Test
    public void createOrderWithItems() throws Exception {
        //when
        Order order = orderService.createOrder(Arrays.asList("melon", "banana", "melon"));

        //then
        assertThat(order.getTotalOriginalPriceInPence(), equalTo(120L));
        assertThat(order.getTotalDiscountInPence(), equalTo(0L));
        assertThat(order.getTotalPriceInPence(), equalTo(120L));

        ImmutableMap<Item, Long> actualItems = order.getItems();
        assertThat(actualItems, notNullValue());
        assertThat(actualItems.size(), equalTo(2));

        assertThat(order.hasItem(Item.MELON), is(true));
        assertThat(order.getItemQuantity(Item.MELON), equalTo(2L));

        assertThat(order.hasItem(Item.BANANA), is(true));
        assertThat(order.getItemQuantity(Item.BANANA), equalTo(1L));
    }

    @Test(expected = UnrecognisedItemException.class)
    public void createOrderWithUnrecognisedItems() throws Exception {
        orderService.createOrder(Arrays.asList("apple", "apple", "banana", "bad item 1", "bad item2"));
    }

    @Test(expected = UnrecognisedItemException.class)
    public void createOrderWithItemNameAsBlank() throws Exception {
        orderService.createOrder(Arrays.asList("apple", "apple", "banana", ""));
    }

    @Test
    public void getOffersForAnQualifyingOrder() throws Exception {
        //given
        when(mockOffer1.appliesTo(mockOrder)).thenReturn(true);
        when(mockOffer2.appliesTo(mockOrder)).thenReturn(false);

        //when
        assertThat(orderService.getOffers(mockOrder), equalTo(newArrayList(mockOffer1)));
    }

    @Test
    public void getOffersForAnOrderWhichDoesNotQualify() throws Exception {
        //given
        when(mockOffer1.appliesTo(mockOrder)).thenReturn(false);
        when(mockOffer2.appliesTo(mockOrder)).thenReturn(false);

        //when
        assertThat(orderService.getOffers(mockOrder), equalTo(newArrayList()));
    }

}