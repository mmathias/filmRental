package com.singular.renting.exception;

public class FilmNotFoundException extends RuntimeException {

    public FilmNotFoundException (Long id) {
        super("Couldn't find film " + id);
    }
}
