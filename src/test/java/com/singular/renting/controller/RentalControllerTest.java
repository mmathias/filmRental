package com.singular.renting.controller;

import com.singular.renting.domain.Rental;
import com.singular.renting.dto.RentalDTO;
import com.singular.renting.resource.RentalAssembler;
import com.singular.renting.service.RentalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RentalControllerTest {

    @Mock
    private RentalService rentalService;
    @Mock
    private RentalAssembler assembler;

    @InjectMocks
    private RentalController controller;

    @Test
    public void isReturningANewRental() {
        RentalDTO rentalDTO = new RentalDTO();
        Rental rental = new Rental();

        when(rentalService.rent(rentalDTO)).thenReturn(rental);
        when(assembler.toModel(rental)).thenReturn(new EntityModel<>(rental));
        ResponseEntity<EntityModel<Rental>> rentalEntity = controller.newRental(rentalDTO);

        verify(rentalService).rent(rentalDTO);
        assertEquals(rental,rentalEntity.getBody().getContent());
    }

    @Test
    public void isFindingARental() {
        Rental rental = new Rental();
        Long id = 123L;
        when(rentalService.getRental(id)).thenReturn(rental);
        when(assembler.toModel(rental)).thenReturn(new EntityModel<>(rental));

        EntityModel<Rental> rentalEntityModel = controller.one(id);

        verify(rentalService).getRental(id);
        assertEquals(rental, rentalEntityModel.getContent());
    }


    // return tests
    @Test
    public void itShouldHaveDaysDelayedAndSurchargesWhenRentalDelays() {

        Rental rental;

        // if there is a delay we calculate the price and update surcharges and days delayed
        // update film inventory



    }

    @Test
    public void itShouldHaveNoDaysDelayedAndSurchargesWhenRentalDoesntDelay() {

        // if there is a delay we calculate the price and update surcharges and days delayed
        // update film inventory



    }

}
