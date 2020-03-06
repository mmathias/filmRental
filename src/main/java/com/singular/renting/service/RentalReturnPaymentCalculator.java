package com.singular.renting.service;

import com.singular.renting.domain.Rental;

public interface RentalReturnPaymentCalculator {

    public Float getRentalReturnPrice(Rental rental);
}
