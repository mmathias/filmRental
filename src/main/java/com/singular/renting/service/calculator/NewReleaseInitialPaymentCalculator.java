package com.singular.renting.service.calculator;

import com.singular.renting.domain.PriceType;

import java.math.BigDecimal;

public class NewReleaseInitialPaymentCalculator implements RentalInitialPaymentCalculator {

    @Override
    public BigDecimal getRentalInitialPrice(int days, PriceType priceType) {
        return priceType.getValue().multiply(BigDecimal.valueOf(days));
    }
}
