package com.singular.renting.service.calculator;

import com.singular.renting.domain.PriceType;
import com.singular.renting.service.calculator.RegularInitialPaymentCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegularInitialPaymentCalculatorTest {

    RegularInitialPaymentCalculator calculator = new RegularInitialPaymentCalculator();

    @Test
    public void itShouldReturn3WhenRentingFor3DaysAPremiumRegularFilm() {
        Float price = calculator.getRentalInitialPrice(3, PriceType.PREMIUM);
        assertEquals(3.0f, price);
    }

    @Test
    public void itShouldReturn3WhenRentingFor2DaysAPremiumRegularFilm() {
        Float price = calculator.getRentalInitialPrice(2, PriceType.PREMIUM);
        assertEquals(3.0f, price);
    }

    @Test
    public void itShouldReturn12WhenRentingFor6DaysAPremiumRegularFilm() {
        Float price = calculator.getRentalInitialPrice(6, PriceType.PREMIUM);
        assertEquals(12.0f, price);
    }

    @Test
    public void itShouldReturn1WhenRentingFor3DaysABasicRegularFilm() {
        Float price = calculator.getRentalInitialPrice(3, PriceType.BASIC);
        assertEquals(1.0f, price);
    }

    @Test
    public void itShouldReturn3WhenRentingFor1DayABasicRegularFilm() {
        Float price = calculator.getRentalInitialPrice(1, PriceType.BASIC);
        assertEquals(1.0f, price);
    }

    @Test
    public void itShouldReturn8WhenRentingFor10DaysABasicRegularFilm() {
        Float price = calculator.getRentalInitialPrice(10, PriceType.BASIC);
        assertEquals(8.0f, price);
    }
}
