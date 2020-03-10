package com.singular.renting.service.calculator;

import com.singular.renting.domain.Rental;

public interface RentalReturnPaymentCalculator {

    public Float getRentalReturnPrice(Rental rental);
}
