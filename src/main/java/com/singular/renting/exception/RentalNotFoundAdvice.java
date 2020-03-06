package com.singular.renting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RentalNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(RentalNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String rentalNotFoundHandler(RentalNotFoundException ex) {
        return ex.getMessage();
    }
}
