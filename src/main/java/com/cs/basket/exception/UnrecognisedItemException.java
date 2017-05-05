package com.cs.basket.exception;

/**
 * Created by ahuja on 5/5/2017.
 */
public class UnrecognisedItemException extends RuntimeException {
    public UnrecognisedItemException(String item) {
        super(String.format("Unrecognised item: '%s'", item));
    }
}
