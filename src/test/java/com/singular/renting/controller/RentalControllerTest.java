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
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;
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

    private static final Long RENTAL_ID = 1L;

    @Test
    public void itShouldReturnANewRentalWhenDTOIsCorrect() {
        RentalDTO rentalDTO = new RentalDTO();
        Rental rental = new Rental();

        when(rentalService.rent(rentalDTO)).thenReturn(rental);
        when(assembler.toModel(rental)).thenReturn(new EntityModel<>(rental));
        ResponseEntity<EntityModel<Rental>> rentalEntity = controller.newRental(rentalDTO);

        verify(rentalService).rent(rentalDTO);
        assertEquals(rental,rentalEntity.getBody().getContent());
    }

    @Test
    public void itShouldReturnARentalByID() {
        Rental rental = new Rental();
        Long id = 123L;
        when(rentalService.get(id)).thenReturn(rental);
        when(assembler.toModel(rental)).thenReturn(new EntityModel<>(rental));

        EntityModel<Rental> rentalEntityModel = controller.getRental(id);

        verify(rentalService).get(id);
        assertEquals(rental, rentalEntityModel.getContent());
    }

    @Test
    public void itShouldReturnARentalWhenReturningFilm() {
        Rental rental = new Rental();

        when(rentalService.returnRental(RENTAL_ID)).thenReturn(rental);
        when(assembler.toModel(rental)).thenReturn(new EntityModel<>(rental));
        ResponseEntity<EntityModel<Rental>> rentalEntity = controller.returnRental(RENTAL_ID);

        verify(rentalService).returnRental(RENTAL_ID);
        assertEquals(rental,rentalEntity.getBody().getContent());
    }

    @Test
    public void itShouldReturnAListOfRentals() {
        Rental rental = new Rental();
        List<Rental> rentals = Collections.singletonList(rental);

        when(rentalService.getAll()).thenReturn(rentals);
        when(assembler.toModel(rental)).thenReturn(new EntityModel<>(rental));
        CollectionModel<EntityModel<Rental>> rentalsOutput = controller.getRentals();

        verify(rentalService).getAll();
        assertTrue(
                rentalsOutput
                        .getContent()
                        .stream()
                        .map(EntityModel::getContent)
                        .allMatch(rental1 -> rental == rental1));
    }
}
