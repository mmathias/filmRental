package com.singular.renting.service;

import com.singular.renting.domain.*;
import com.singular.renting.dto.RentalDTO;
import com.singular.renting.exception.RentalNotFoundException;
import com.singular.renting.repository.RentalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RentalServiceTest {

    @Mock
    CustomerService customerService;
    @Mock
    FilmService filmService;
    @Mock
    RentalRepository rentalRepository;

    @InjectMocks
    RentalService service;

    private static final Long RENTAL_ID = 1L;
    private static final Long CUSTOMER_ID = 1L;
    private static final Long FILM_ID = 1L;
    private static final int FILMS_AVAILABLE = 1;
    private static final int CUSTOMER_POINTS = 1;

    @Test
    public void isUpdatingInventoryCalculatingPointsAndPrice() {
        RentalDTO rentalDTO = mockRentalDTO();
        Film film = mockFilm();
        mockCustomer(film);

        Rental rental = service.rent(rentalDTO);

        assertNotNull(rental);
        assertNotNull(rental.getFilm());
        assertNotNull(rental.getCustomer());

        // calculate price
        assertEquals(BigDecimal.valueOf(5.0), rental.getPrice());
        assertEquals(rentalDTO.getDays(), rental.getDays());
        assertEquals(LocalDate.now().getDayOfMonth(), rental.getInitialDate().getDayOfMonth());
        assertEquals(LocalDate.now().getMonth(), rental.getInitialDate().getMonth());
        assertEquals(LocalDate.now().getYear(), rental.getInitialDate().getYear());

        verify(filmService).getAndUpdateInventoryFilm(anyLong());
        verify(customerService).getCustomerAndCalculateBonusPoints(anyLong(), anyInt());
        verify(rentalRepository).save(any(Rental.class));
    }

    @Test
    public void itShouldReturnARentalIfRentalExists() {
        Long RENTAL_ID = 1L;
        Rental rental = new Rental();
        when(rentalRepository.findById(RENTAL_ID)).thenReturn(Optional.of(rental));

        Rental rentalOutput = service.get(RENTAL_ID);

        assertEquals(rental, rentalOutput);
    }

    @Test
    public void itShouldReturnAListOfRentals() {
        List<Rental> rentals = Collections.singletonList(new Rental());
        when(rentalRepository.findAll()).thenReturn(rentals);

        List<Rental> rentalsOutput = service.getAll();

        assertEquals(rentals, rentalsOutput);
    }

    @Test
    public void itShouldReturnARentalNotFoundExceptionIfRentalDoesntExist() {
        Long RENTAL_ID = 1L;

        RentalNotFoundException exception = assertThrows(
                RentalNotFoundException.class,
                () -> service.get(RENTAL_ID));

        assertEquals("Couldn't find rental " + RENTAL_ID, exception.getMessage());
    }

    @Test
    public void itShouldHaveDaysDelayedAndSurchargesWhenRentalDelays() {
        Rental previousRental = new Rental();
        previousRental.setInitialDate(LocalDate.now().minusDays(20));
        previousRental.setDays(5);

        when(rentalRepository.findById(RENTAL_ID)).thenReturn(java.util.Optional.of(previousRental));

        Rental rental = service.returnRental(RENTAL_ID);

        assertEquals(15, rental.getDaysDelayed());
        assertEquals(BigDecimal.valueOf(30.0), rental.getSurcharges());

        verify(rentalRepository).save(any(Rental.class));
    }

    @Test
    public void itShouldHaveNoDaysDelayedAndSurchargesWhenRentalDoesntDelay() {
        Rental previousRental = new Rental();
        previousRental.setInitialDate(LocalDate.now().minusDays(4));
        previousRental.setDays(15);

        when(rentalRepository.findById(RENTAL_ID)).thenReturn(java.util.Optional.of(previousRental));

        Rental rental = service.returnRental(RENTAL_ID);

        assertEquals(0, rental.getDaysDelayed());
        assertEquals(BigDecimal.ZERO, rental.getSurcharges());

        verify(rentalRepository).save(any(Rental.class));
    }

    private Customer mockCustomer(Film film) {
        Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);
        customer.setBonusPoints(CUSTOMER_POINTS);

        when(customerService.getCustomerAndCalculateBonusPoints(CUSTOMER_ID, film.getFilmType().getPoints()))
                .thenReturn(customer);

        return customer;
    }

    private RentalDTO mockRentalDTO() {
        RentalDTO rentalDTO = new RentalDTO();
        rentalDTO.setCustomerId(CUSTOMER_ID);
        rentalDTO.setFilmId(FILM_ID);
        rentalDTO.setDays(5);

        return rentalDTO;
    }

    private Film mockFilm() {
        Film film = new Film();
        film.setId(FILM_ID);
        film.setQuantity(FILMS_AVAILABLE);
        film.setFilmType(FilmType.NEW_RELEASE);
        film.setPriceType(PriceType.BASIC);

        when(filmService.getAndUpdateInventoryFilm(FILM_ID)).thenReturn(film);

        return film;
    }
}
