package com.singular.renting.service;

import ch.qos.logback.core.rolling.helper.RenameUtil;
import com.singular.renting.domain.Customer;
import com.singular.renting.domain.Film;
import com.singular.renting.domain.Rental;
import com.singular.renting.dto.RentalDTO;
import com.singular.renting.exception.CustomerNotFoundException;
import com.singular.renting.exception.FilmNotFoundException;
import com.singular.renting.exception.NotEnoughFilmsException;
import com.singular.renting.exception.RentalNotFoundException;
import com.singular.renting.factory.RentalInitialPaymentCalculatorFactory;
import com.singular.renting.repository.CustomerRepository;
import com.singular.renting.repository.FilmRepository;
import com.singular.renting.repository.RentalRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;

@Component
public class RentalService {

    private FilmRepository filmRepository;
    private CustomerRepository customerRepository;
    private RentalRepository rentalRepository;

    public RentalService (
            FilmRepository filmRepository,
            CustomerRepository customerRepository,
            RentalRepository rentalRepository
    ) {
        this.filmRepository = filmRepository;
        this.customerRepository = customerRepository;
        this.rentalRepository = rentalRepository;
    }

    public Rental rent(RentalDTO rentalDTO) {
        // get film and update inventory
        Film film = getAndUpdateInventoryFilm(rentalDTO.getFilmId());

        // get customer and calculate points, update
        Customer customer = getCustomerAndCalculateBonusPoints(
                rentalDTO.getCustomerId(),
                film.getFilmType().getPoints());

        // calculate price
        Rental rental = new Rental();
        RentalInitialPaymentCalculator calculator = new RentalInitialPaymentCalculatorFactory().make(film.getFilmType());
        Float price = calculator.getRentalInitialPrice(rentalDTO.getDays(), film.getPriceType());

        // return rental price
        return saveRental(rentalDTO.getDays(), film, customer, rental, price);
    }

    public Rental returnRental(Long rentalId) {
        Rental rental = getRental(rentalId);
        // it should find the film
        // it should update the quantity
        incrementFilmInventory(rental.getFilm());

        // update surcharge and days delayed if return date is greater than start date plus days
        updateSurchargeAndDaysDelayed(rental);

        return rental;
    }

    private void updateSurchargeAndDaysDelayed(Rental rental) {
        int daysDelayed;

        LocalDate expectedReturnDate = rental.getInitialDate().plusDays(rental.getDays());
        if (expectedReturnDate.isAfter(LocalDate.now())) {
            daysDelayed = Period.between(LocalDate.now(), expectedReturnDate).getDays();
            rental.setSurcharges(daysDelayed * 2f);
            rental.setDaysDelayed(daysDelayed);
        }

        rentalRepository.save(rental);
    }

    private Film incrementFilmInventory(Film film) {
        increaseQuantity(film);

        filmRepository.save(film);

        return film;
    }

    private void increaseQuantity(Film film) {
        film.setQuantity(film.getQuantity() + 1);
    }

    private Rental saveRental(int days, Film film, Customer customer, Rental rental, Float price) {
        rental.setFilm(film);
        rental.setCustomer(customer);
        rental.setPrice(price);
        rental.setDays(days);
        rental.setInitialDate(LocalDate.now());

        rentalRepository.save(rental);

        return rental;
    }

    private Customer getCustomerAndCalculateBonusPoints(Long customerId, int bonusPoints) {
        Customer customer = getCustomer(customerId);
        updateCustomerPoints(customer, bonusPoints);

        customerRepository.save(customer);

        return customer;
    }

    private void updateCustomerPoints(Customer customer, int bonusPoints) {
        customer.setBonusPoints(customer.getBonusPoints() + bonusPoints);
    }

    private Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    private Film getAndUpdateInventoryFilm(Long filmId) {
        Film film = getFilm(filmId);
        decreaseQuantity(film);

        filmRepository.save(film);

        return film;
    }

    private Film getFilm(Long filmId) {
        return filmRepository.findById(filmId)
                    .orElseThrow(() -> new FilmNotFoundException(filmId));
    }

    private void decreaseQuantity(Film film) {
        int quantity = film.getQuantity() - 1;
        if (quantity < 0) throw new NotEnoughFilmsException(film.getId());
        film.setQuantity(quantity);
    }

    public Rental getRental(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new RentalNotFoundException(id));
    }

    public List<Rental> getAll() {
        return rentalRepository.findAll();
    }
}
