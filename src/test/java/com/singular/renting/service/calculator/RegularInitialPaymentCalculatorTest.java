package com.singular.renting.service.calculator;

import com.singular.renting.domain.PriceType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegularInitialPaymentCalculatorTest {

    RegularInitialPaymentCalculator calculator = new RegularInitialPaymentCalculator();

    @Test
    public void itShouldReturn3WhenRentingFor3DaysAPremiumRegularFilm() {
        BigDecimal price = calculator.getRentalInitialPrice(3, PriceType.PREMIUM);
        assertEquals(BigDecimal.valueOf(3.0), price);
    }

    @Test
    public void itShouldReturn3WhenRentingFor2DaysAPremiumRegularFilm() {
        BigDecimal price = calculator.getRentalInitialPrice(2, PriceType.PREMIUM);
        assertEquals(BigDecimal.valueOf(3.0), price);
    }

    @Test
    public void itShouldReturn12WhenRentingFor6DaysAPremiumRegularFilm() {
        BigDecimal price = calculator.getRentalInitialPrice(6, PriceType.PREMIUM);
        assertEquals(BigDecimal.valueOf(12.0), price);
    }

    @Test
    public void itShouldReturn1WhenRentingFor3DaysABasicRegularFilm() {
        BigDecimal price = calculator.getRentalInitialPrice(3, PriceType.BASIC);
        assertEquals(BigDecimal.valueOf(1.0), price);
    }

    @Test
    public void itShouldReturn3WhenRentingFor1DayABasicRegularFilm() {
        BigDecimal price = calculator.getRentalInitialPrice(1, PriceType.BASIC);
        assertEquals(BigDecimal.valueOf(1.0), price);
    }

    @Test
    public void itShouldReturn8WhenRentingFor10DaysABasicRegularFilm() {
        BigDecimal price = calculator.getRentalInitialPrice(10, PriceType.BASIC);
        assertEquals(BigDecimal.valueOf(8.0), price);
    }
}
