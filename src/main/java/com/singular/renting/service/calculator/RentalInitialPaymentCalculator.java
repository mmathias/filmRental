package com.singular.renting.service.calculator;

import com.singular.renting.domain.PriceType;

public interface RentalInitialPaymentCalculator {

    Float getRentalInitialPrice(int days, PriceType priceType);
}
