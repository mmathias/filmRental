package com.singular.renting.service.calculator;

import com.singular.renting.domain.FilmType;
import com.singular.renting.domain.Rental;
import org.springframework.stereotype.Component;

@Component
public class RentalBonusPointsCalculator {

    private final int NEW_RELEASE_BONUS_POINTS = 2;
    private final int REGULAR_RELEASE_BONUS_POINTS = 1;

    public int getRentalBonusPoints(Rental rental){
        if(rental.getFilm().getFilmType() == FilmType.NEW_RELEASE) {
            return NEW_RELEASE_BONUS_POINTS;
        } else {
            return REGULAR_RELEASE_BONUS_POINTS;
        }
    }
}
