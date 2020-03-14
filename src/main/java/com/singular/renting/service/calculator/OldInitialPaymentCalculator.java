package com.singular.renting.service.calculator;

import com.singular.renting.domain.PriceType;

import java.math.BigDecimal;

public class OldInitialPaymentCalculator implements RentalInitialPaymentCalculator {

    private final int RENTAL_DAYS_DURATION = 5;

    @Override
    public BigDecimal getRentalInitialPrice(int days, PriceType priceType) {
        BigDecimal price = priceType.getValue();

        return price.add(days > RENTAL_DAYS_DURATION ?
                price.multiply(BigDecimal.valueOf(days - RENTAL_DAYS_DURATION)) : BigDecimal.ZERO);
    }

}
