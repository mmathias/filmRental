package com.singular.renting.service.calculator;

import com.singular.renting.domain.PriceType;

import java.math.BigDecimal;

public interface RentalInitialPaymentCalculator {

    BigDecimal getRentalInitialPrice(int days, PriceType priceType);
}
