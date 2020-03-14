package com.singular.renting.service.calculator;

import com.singular.renting.domain.PriceType;

import java.math.BigDecimal;

public class RegularInitialPaymentCalculator implements RentalInitialPaymentCalculator{

    private final int RENTAL_DAYS_DURATION = 3;

    @Override
    public BigDecimal getRentalInitialPrice(int days, PriceType priceType) {
        BigDecimal price = priceType.getValue();
        price = price.add(days > RENTAL_DAYS_DURATION ?
                price.multiply(BigDecimal.valueOf(days - RENTAL_DAYS_DURATION)) : BigDecimal.ZERO);

        return price;
    }
}
