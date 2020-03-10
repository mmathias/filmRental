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
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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

    private static final Long RENTAL_ID = 1L;
    private static final Long CUSTOMER_ID = 1L;
    private static final Long FILM_ID = 1L;

    @Test
    public void isUpdatingInventoryCalculatingPointsAndPrice() {
        int filmsAvailable = 5;
        int customerPoints = 35;

        RentalDTO rentalDTO = mockRentalDTO();
        mockFilm(filmsAvailable);
        mockCustomer(customerPoints);

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
        assertEquals(LocalDate.now().getDayOfMonth(), rental.getInitialDate().getDayOfMonth());
        assertEquals(LocalDate.now().getMonth(), rental.getInitialDate().getMonth());
        assertEquals(LocalDate.now().getYear(), rental.getInitialDate().getYear());

        verify(filmRepository).save(any(Film.class));
        verify(customerRepository).save(any(Customer.class));
        verify(rentalRepository).save(any(Rental.class));
    }

    @Test
    public void itShouldThrowAnExceptionWhenFilmNotFound() {
        RentalDTO rentalDTO = new RentalDTO();
        rentalDTO.setFilmId(FILM_ID);

        FilmNotFoundException exception = assertThrows(
                FilmNotFoundException.class,
                () -> service.rent(rentalDTO));

        assertEquals("Couldn't find film " + rentalDTO.getFilmId(), exception.getMessage());
    }

    // return tests
    @Test
    public void itShouldHaveDaysDelayedAndSurchargesWhenRentalDelays() {
        int filmsAvailable = 2;
        RentalDTO rentalDTO = mockRentalDTO();
        mockFilm(filmsAvailable);

        Rental rental = service.returnRental(RENTAL_ID);

        assertEquals(filmsAvailable + 1, rental.getFilm().getQuantity());

        assertEquals(12, rental.getDaysDelayed());
        assertEquals(120, rental.getSurcharges());
    }

    @Test
    public void itShouldHaveNoDaysDelayedAndSurchargesWhenRentalDoesntDelay() {

    }

    private Customer mockCustomer(int customerPoints) {
        Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);
        customer.setBonusPoints(customerPoints);

        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(java.util.Optional.of(customer));

        return customer;
    }

    private RentalDTO mockRentalDTO() {
        RentalDTO rentalDTO = new RentalDTO();
        rentalDTO.setCustomerId(CUSTOMER_ID);
        rentalDTO.setFilmId(FILM_ID);
        rentalDTO.setDays(5);

        return rentalDTO;
    }

    private Film mockFilm(int filmsAvailable) {
        Film film = new Film();
        film.setId(FILM_ID);
        film.setQuantity(filmsAvailable);
        film.setFilmType(FilmType.NEW_RELEASE);
        film.setPriceType(PriceType.BASIC);

        when(filmRepository.findById(FILM_ID)).thenReturn(java.util.Optional.of(film));

        return film;
    }
}
