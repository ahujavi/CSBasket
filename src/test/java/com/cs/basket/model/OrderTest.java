package com.cs.basket.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * Created by ahuja on 3/26/2016.
 */
@RunWith(JUnit4.class)
public class OrderTest {

    @Test
    public void testGetTotalDiscountForAnOrderWithDiscount() throws Exception {
        //given
        Order order = new Order(new HashMap<Item, Long>(), 200);

        //when
        order.addDiscount(20);
        order.addDiscount(10);

        //then
        assertThat(order.getTotalDiscountInPence(), equalTo(30L));
    }

    @Test
    public void testGetTotalDiscountForAnOrderWithoutDiscount() throws Exception {
        //given
        Order order = new Order(new HashMap<Item, Long>(), 200);

        //then
        assertThat(order.getTotalDiscountInPence(), equalTo(0L));
    }
}