package com.singular.renting.service;

import com.singular.renting.domain.PriceType;
import com.singular.renting.service.calculator.NewReleaseInitialPaymentCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewReleaseInitialPaymentCalculatorTest {

    NewReleaseInitialPaymentCalculator calculator = new NewReleaseInitialPaymentCalculator();

    // it should return 3 if I rent for 1 day a premium new release movie
    @Test
    public void itShouldReturn3WhenRentingFor1DayAPremiumNewRelease() {
        Float price = calculator.getRentalInitialPrice(1, PriceType.PREMIUM);
        assertEquals(3.0f, price);
    }

    // it should return 6 if I rent for 2 day a premium new release movie
    @Test
    public void itShouldReturn6WhenRentingFor2DayAPremiumNewRelease() {
        Float price = calculator.getRentalInitialPrice(2, PriceType.PREMIUM);
        assertEquals(6.0f, price);
    }

    // it should return 4 if I rent for 4 day a basic new release movie
    @Test
    public void itShouldReturn4WhenRentingFor4DayABasicNewRelease() {
        Float price = calculator.getRentalInitialPrice(4, PriceType.BASIC);
        assertEquals(4.0f, price);
    }

    // it should return 7 if I rent for 7 day a basic new release movie
    @Test
    public void itShouldReturn7WhenRentingFor7DayABasicNewRelease() {
        Float price = calculator.getRentalInitialPrice(7, PriceType.BASIC);
        assertEquals(7.0f, price);
    }
}
