package com.singular.renting.factory;

import com.singular.renting.domain.FilmType;
import com.singular.renting.service.calculator.NewReleaseInitialPaymentCalculator;
import com.singular.renting.service.calculator.OldInitialPaymentCalculator;
import com.singular.renting.service.calculator.RegularInitialPaymentCalculator;
import com.singular.renting.service.calculator.RentalInitialPaymentCalculator;
import org.springframework.stereotype.Component;

@Component
public class RentalInitialPaymentCalculatorFactory {

    public RentalInitialPaymentCalculator make(FilmType filmType) {
        RentalInitialPaymentCalculator calculator = null;

        switch (filmType){
            case NEW_RELEASE:
                calculator = new NewReleaseInitialPaymentCalculator();
                break;
            case REGULAR:
                calculator = new RegularInitialPaymentCalculator();
                break;
            case OLD:
                calculator = new OldInitialPaymentCalculator();
                break;
        }

        return calculator;
    }
}
