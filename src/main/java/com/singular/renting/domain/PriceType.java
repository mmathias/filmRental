package com.singular.renting.domain;

public enum PriceType {
    PREMIUM (3.0f),
    BASIC   (1.0f);

    // melhor usar BigDecimal pra valores quebrados
    private final Float value;

    PriceType(Float value) {
        this.value = value;
    }

    public Float getValue() {
        return value;
    }
}
