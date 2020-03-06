package com.singular.renting.service;

import com.singular.renting.domain.PriceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OldInitialPaymentCalculatorTest {

    OldInitialPaymentCalculator calculator = new OldInitialPaymentCalculator();

    // it should return 3 if I rent for 5 days a premium old movie
    @Test
    public void itShouldReturn3WhenRentingFor5DayAPremiumOld() {
        Float price = calculator.getRentalInitialPrice(5, PriceType.PREMIUM);
        assertEquals(3.0f, price);
    }

    // it should return 3 if I rent for 3 days a premium old movie
    @Test
    public void itShouldReturn3WhenRentingFor3DayAPremiumOld() {
        Float price = calculator.getRentalInitialPrice(3, PriceType.PREMIUM);
        assertEquals(3.0f, price);
    }

    @Test
    public void itShouldReturn9WhenRentingFor7DayAPremiumOld() {
        Float price = calculator.getRentalInitialPrice(7, PriceType.PREMIUM);
        assertEquals(9.0f, price);
    }

    @Test
    public void itShouldReturn1WhenRentingFor5DayABasicOld() {
        Float price = calculator.getRentalInitialPrice(5, PriceType.BASIC);
        assertEquals(1.0f, price);
    }

    @Test
    public void itShouldReturn1WhenRentingFor3DayABasicOld() {
        Float price = calculator.getRentalInitialPrice(3, PriceType.BASIC);
        assertEquals(1.0f, price);
    }

    @Test
    public void itShouldReturn3WhenRentingFor7DayAPremiumOld() {
        Float price = calculator.getRentalInitialPrice(7, PriceType.BASIC);
        assertEquals(3.0f, price);
    }
}
