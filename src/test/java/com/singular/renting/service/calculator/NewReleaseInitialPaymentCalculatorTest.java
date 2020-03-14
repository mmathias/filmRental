package com.singular.renting.service.calculator;

import com.singular.renting.domain.PriceType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewReleaseInitialPaymentCalculatorTest {

    NewReleaseInitialPaymentCalculator calculator = new NewReleaseInitialPaymentCalculator();

    @Test
    public void itShouldReturn3WhenRentingFor1DayAPremiumNewRelease() {
        BigDecimal price = calculator.getRentalInitialPrice(1, PriceType.PREMIUM);
        assertEquals(BigDecimal.valueOf(3.0), price);
    }

    @Test
    public void itShouldReturn6WhenRentingFor2DayAPremiumNewRelease() {
        BigDecimal price = calculator.getRentalInitialPrice(2, PriceType.PREMIUM);
        assertEquals(BigDecimal.valueOf(6.0), price);
    }

    @Test
    public void itShouldReturn4WhenRentingFor4DayABasicNewRelease() {
        BigDecimal price = calculator.getRentalInitialPrice(4, PriceType.BASIC);
        assertEquals(BigDecimal.valueOf(4.0), price);
    }

    @Test
    public void itShouldReturn7WhenRentingFor7DayABasicNewRelease() {
        BigDecimal price = calculator.getRentalInitialPrice(7, PriceType.BASIC);
        assertEquals(BigDecimal.valueOf(7.0), price);
    }
}
