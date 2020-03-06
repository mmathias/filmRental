package com.singular.renting.service;

import com.singular.renting.domain.PriceType;

public interface RentalInitialPaymentCalculator {

    Float getRentalInitialPrice(int days, PriceType priceType);
}
