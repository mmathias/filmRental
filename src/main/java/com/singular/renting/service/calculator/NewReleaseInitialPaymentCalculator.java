package com.singular.renting.service.calculator;

import com.singular.renting.domain.PriceType;

public class NewReleaseInitialPaymentCalculator implements RentalInitialPaymentCalculator {

    @Override
    public Float getRentalInitialPrice(int days, PriceType priceType) {

        return priceType.getValue() * days;
    }
}
