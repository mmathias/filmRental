package com.singular.renting.domain;

public enum FilmType {
    NEW_RELEASE (2),
    REGULAR (1),
    OLD (1);

    private int points;

    FilmType(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}
