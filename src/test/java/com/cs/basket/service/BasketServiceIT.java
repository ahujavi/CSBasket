package com.cs.basket.service;

import com.cs.basket.app.BasketApplicationConfig;
import com.cs.basket.exception.UnrecognisedItemException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Integration test for Basket Service
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BasketApplicationConfig.class, loader=AnnotationConfigContextLoader.class)
public class BasketServiceIT {
    @Autowired
    BasketService basketService;

    @Test
    public void priceBasketWithDiscounts() throws UnrecognisedItemException {
        checkPrice(105, "apple", "banana", "melon", "melon");
        checkPrice(85, "apple", "banana", "lime", "lime", "lime");
        checkPrice(135, "apple", "banana", "melon", "melon", "lime", "lime", "lime");
        checkPrice(155, "apple", "banana", "melon", "melon", "melon");
        checkPrice(100, "apple", "banana", "lime", "lime", "lime", "lime");
    }

    @Test
    public void priceBasketWithNoDiscounts() throws UnrecognisedItemException {
        checkPrice(110, "apple", "apple", "banana", "banana");
    }

    @Test
    public void priceBasketWithNoItems() throws UnrecognisedItemException {
        checkPrice(0);
    }

    @Test(expected = UnrecognisedItemException.class)
    public void priceBasketWithUnrecognisedItem() throws UnrecognisedItemException {
        basketService.priceBasket(Arrays.asList("apple", "banana", "bad item"));
    }

    @Test(expected = UnrecognisedItemException.class)
    public void priceBasketWithBlankItem() throws UnrecognisedItemException {
        basketService.priceBasket(Arrays.asList("apple", "banana", "  "));
    }

    private void checkPrice(long expectedPrice, String... items) throws UnrecognisedItemException {
        assertThat("Price Mismatch!!", basketService.priceBasket(Arrays.asList(items)), equalTo(expectedPrice));
    }
}
