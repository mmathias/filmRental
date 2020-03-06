package com.singular.renting.service;

import com.singular.renting.domain.*;
import com.singular.renting.dto.RentalDTO;
import com.singular.renting.exception.FilmNotFoundException;
import com.singular.renting.repository.CustomerRepository;
import com.singular.renting.repository.FilmRepository;
import com.singular.renting.repository.RentalRepository;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RentalServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    RentalRepository rentalRepository;
    @Mock
    FilmRepository filmRepository;
    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    RentalService service;

    @Test
    public void isUpdatingInventoryCalculatingPointsAndPrice() {
        RentalDTO rentalDTO = new RentalDTO();
        rentalDTO.setCustomerId(2L);
        rentalDTO.setFilmId(3L);
        rentalDTO.setDays(5);
        int filmsAvailable = 5;
        int customerPoints = 35;
        Film film = new Film();
        film.setQuantity(filmsAvailable);
        film.setFilmType(FilmType.NEW_RELEASE);
        film.setPriceType(PriceType.BASIC);
        Customer customer = new Customer();
        customer.setBonusPoints(customerPoints);
        Rental rentalMock = new Rental();
        rentalMock.setInitialDate(new Date());
        rentalMock.setDays(rentalDTO.getDays());
        rentalMock.setFilm(film);
        rentalMock.setCustomer(customer);
//        rentalMock.setPrice(service.);

        when(filmRepository.findById(rentalDTO.getFilmId())).thenReturn(java.util.Optional.of(film));
        when(customerRepository.findById(rentalDTO.getCustomerId())).thenReturn(java.util.Optional.of(customer));
        when(rentalRepository.save(any(Rental.class))).thenReturn(rentalMock);

        Rental rental = service.rent(rentalDTO);

        assertNotNull(rental);

        assertNotNull(rental.getFilm());
        assertEquals(filmsAvailable - 1, rental.getFilm().getQuantity());

        // get customer and calculate points, update
        assertNotNull(rental.getCustomer());
        assertEquals(customerPoints + 2, rental.getCustomer().getBonusPoints());

        // calculate price
        assertEquals(5.0f, rental.getPrice());
        assertEquals(rentalDTO.getDays(), rental.getDays());
        assertEquals(5.0f, rental.getInitialDate());
    }

    @Test
    public void itShouldThrowAnExceptionWhenFilmNotFound() {
        RentalDTO rentalDTO = new RentalDTO();
        rentalDTO.setFilmId(3L);

        FilmNotFoundException exception = assertThrows(
                FilmNotFoundException.class,
                () -> service.rent(rentalDTO));

        assertEquals("Couldn't find film " + rentalDTO.getFilmId(), exception.getMessage());
    }
}
