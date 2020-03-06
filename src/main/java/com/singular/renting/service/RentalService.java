package com.singular.renting.service;

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

import java.util.Date;

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

    private Rental saveRental(int days, Film film, Customer customer, Rental rental, Float price) {
        rental.setFilm(film);
        rental.setCustomer(customer);
        rental.setPrice(price);
        rental.setDays(days);
        rental.setInitialDate(new Date());

        return rentalRepository.save(rental);
    }

    private Customer getCustomerAndCalculateBonusPoints(Long customerId, int bonusPoints) {
        Customer customer = getCustomer(customerId);
        updateCustomerPoints(customer, bonusPoints);
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


}
