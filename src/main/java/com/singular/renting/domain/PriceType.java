package com.singular.renting.domain;

import java.math.BigDecimal;

public enum PriceType {
    PREMIUM (BigDecimal.valueOf(3.0)),
    BASIC   (BigDecimal.valueOf(1.0));

    private final BigDecimal value;

    PriceType(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }
}
