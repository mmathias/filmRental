package com.singular.renting.service.calculator;

import com.singular.renting.domain.PriceType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OldInitialPaymentCalculatorTest {

    OldInitialPaymentCalculator calculator = new OldInitialPaymentCalculator();

    @Test
    public void itShouldReturn3WhenRentingFor5DayAPremiumOld() {
        BigDecimal price = calculator.getRentalInitialPrice(5, PriceType.PREMIUM);
        assertEquals(BigDecimal.valueOf(3.0), price);
    }

    @Test
    public void itShouldReturn3WhenRentingFor3DayAPremiumOld() {
        BigDecimal price = calculator.getRentalInitialPrice(3, PriceType.PREMIUM);
        assertEquals(BigDecimal.valueOf(3.0), price);
    }

    @Test
    public void itShouldReturn9WhenRentingFor7DayAPremiumOld() {
        BigDecimal price = calculator.getRentalInitialPrice(7, PriceType.PREMIUM);
        assertEquals(BigDecimal.valueOf(9.0), price);
    }

    @Test
    public void itShouldReturn1WhenRentingFor5DayABasicOld() {
        BigDecimal price = calculator.getRentalInitialPrice(5, PriceType.BASIC);
        assertEquals(BigDecimal.valueOf(1.0), price);
    }

    @Test
    public void itShouldReturn1WhenRentingFor3DayABasicOld() {
        BigDecimal price = calculator.getRentalInitialPrice(3, PriceType.BASIC);
        assertEquals(BigDecimal.valueOf(1.0), price);
    }

    @Test
    public void itShouldReturn3WhenRentingFor7DayAPremiumOld() {
        BigDecimal price = calculator.getRentalInitialPrice(7, PriceType.BASIC);
        assertEquals(BigDecimal.valueOf(3.0), price);
    }
}
