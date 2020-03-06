package com.singular.renting.exception;

public class NotEnoughFilmsException extends RuntimeException {

    public NotEnoughFilmsException (Long id) {
        super("Not enough films " + id);
    }

}
