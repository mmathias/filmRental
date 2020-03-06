package com.singular.renting.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmTypeTest {

    @Test
    public void itShouldReturn2PointsWhenRentingANewRelease() {
        int points = FilmType.NEW_RELEASE.getPoints();
        assertEquals(2, points);
    }

    @Test
    public void itShouldReturn1PointWhenRentingARegular() {
        int points = FilmType.REGULAR.getPoints();
        assertEquals(1, points);
    }

    @Test
    public void itShouldReturn1PointWhenRentingAnOld() {
        int points = FilmType.OLD.getPoints();
        assertEquals(1, points);
    }

}
