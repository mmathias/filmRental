package com.singular.renting.service;

import com.singular.renting.domain.PriceType;

public class OldInitialPaymentCalculator implements RentalInitialPaymentCalculator {

    private final int RENTAL_DAYS_DURATION = 5;

    @Override
    public Float getRentalInitialPrice(int days, PriceType priceType) {
        Float price = priceType.getValue();
        price += days > RENTAL_DAYS_DURATION ? (days - RENTAL_DAYS_DURATION) * price : 0;

        return price;
    }

}
