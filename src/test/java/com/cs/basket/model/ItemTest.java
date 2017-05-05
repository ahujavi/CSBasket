package com.cs.basket.model;

import com.cs.basket.exception.UnrecognisedItemException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * Created by ahuja on 3/26/2016.
 */
public class ItemTest {

    @Test
    public void canHandleExactMatch() throws Exception {
        assertThat(Item.itemFor("apple"), equalTo(Item.APPLE));
        assertThat(Item.itemFor("  apple  "), equalTo(Item.APPLE));
    }

    @Test
    public void ignoresCase() throws Exception {
        assertThat(Item.itemFor("APPle"), equalTo(Item.APPLE));
        assertThat(Item.itemFor("   APPle  "), equalTo(Item.APPLE));
    }

    @Test(expected = UnrecognisedItemException.class)
    public void testUnrecognisedItem() throws Exception {
        Item.itemFor("unrecognised");
    }

    @Test(expected = UnrecognisedItemException.class)
    public void testNullItem() throws Exception {
        Item.itemFor(null);
    }

    @Test(expected = UnrecognisedItemException.class)
    public void testBlankItem() throws Exception {
        Item.itemFor("  ");
    }

    @Test(expected = UnrecognisedItemException.class)
    public void testEmptyItem() throws Exception {
        Item.itemFor("");
    }
}