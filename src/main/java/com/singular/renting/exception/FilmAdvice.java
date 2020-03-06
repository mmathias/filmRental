package com.singular.renting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FilmAdvice {

    @ResponseBody
    @ExceptionHandler(FilmNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String filmNotFoundAdvice(FilmNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(NotEnoughFilmsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String notEnoughFilmsAdvice(NotEnoughFilmsException ex) {
        return ex.getMessage();
    }
}
