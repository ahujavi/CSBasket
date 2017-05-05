package com.cs.basket.service;

import com.cs.basket.exception.UnrecognisedItemException;
import com.cs.basket.model.Order;
import com.cs.basket.offers.Offer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by ahuja on 3/26/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class BasketServiceTest {

    @Mock
    OrderService orderService;

    @InjectMocks
    BasketService basketService = new BasketServiceImpl();

    @Mock
    Order order;

    @Mock
    Offer mockOffer1;

    @Mock
    Offer mockOffer2;

    @Mock
    List<String> items;

    @Test
    public void priceBasketWithOffers() throws Exception {
        //given
        long expectedTotalPrice = 220;
        long discount1 = 20;
        long discount2 = 10;

        when(orderService.createOrder(items)).thenReturn(order);
        when(orderService.getOffers(order)).thenReturn(Arrays.asList(mockOffer1, mockOffer2));
        when(mockOffer1.calculateDiscount(order)).thenReturn(discount1);
        when(mockOffer2.calculateDiscount(order)).thenReturn(discount2);
        when(order.getTotalPriceInPence()).thenReturn(expectedTotalPrice);

        //when
        long actualTotalPrice = basketService.priceBasket(items);

        //then
        verify(orderService).createOrder(items);
        verify(orderService).getOffers(order);
        verify(mockOffer1).calculateDiscount(order);
        verify(order).addDiscount(discount1);
        verify(mockOffer2).calculateDiscount(order);
        verify(order).addDiscount(discount2);
        verify(order).getTotalPriceInPence();

        assertThat(actualTotalPrice, equalTo(expectedTotalPrice));
    }

    @Test
    public void priceBasketWithNoOffers() throws Exception {
        //given
        long expectedTotalPrice = 220;

        when(orderService.createOrder(items)).thenReturn(order);
        when(orderService.getOffers(order)).thenReturn(new ArrayList<Offer>());
        when(order.getTotalPriceInPence()).thenReturn(expectedTotalPrice);

        //when
        long actualTotalPrice = basketService.priceBasket(items);

        //then
        verify(orderService).createOrder(items);
        verify(orderService).getOffers(order);
        verify(order, times(0)).addDiscount(anyLong());
        verify(order).getTotalPriceInPence();

        assertThat(actualTotalPrice, equalTo(expectedTotalPrice));
    }

    @Test(expected = UnrecognisedItemException.class)
    public void priceBasketWithUnrecognisedItems() throws Exception {
        when(orderService.createOrder(any(List.class))).thenThrow(new UnrecognisedItemException("mock exception"));

        //when
        basketService.priceBasket(Arrays.asList("apple", "banana"));
    }
}