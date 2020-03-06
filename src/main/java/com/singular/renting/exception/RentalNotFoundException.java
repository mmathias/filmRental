package com.singular.renting.exception;

public class RentalNotFoundException extends RuntimeException {

    public RentalNotFoundException(Long id) {
        super("Couldn't find rental " + id);
    }
}
