package com.singular.renting.service;

import com.singular.renting.domain.Customer;
import com.singular.renting.domain.Film;
import com.singular.renting.domain.Rental;
import com.singular.renting.dto.RentalDTO;
import com.singular.renting.exception.RentalNotFoundException;
import com.singular.renting.factory.RentalInitialPaymentCalculatorFactory;
import com.singular.renting.repository.RentalRepository;
import com.singular.renting.service.calculator.RentalInitialPaymentCalculator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Component
public class RentalService {

    private RentalRepository rentalRepository;
    private FilmService filmService;
    private CustomerService customerService;

    public RentalService (
            FilmService filmService,
            CustomerService customerService,
            RentalRepository rentalRepository) {
        this.filmService = filmService;
        this.customerService = customerService;
        this.rentalRepository = rentalRepository;
    }

    public Rental rent(RentalDTO rentalDTO) {
        Film film = filmService.getAndUpdateInventoryFilm(rentalDTO.getFilmId());

        Customer customer = customerService.getCustomerAndCalculateBonusPoints(
                rentalDTO.getCustomerId(),
                film.getFilmType().getPoints());

        // calculate price
        Rental rental = new Rental();
        RentalInitialPaymentCalculator calculator = new RentalInitialPaymentCalculatorFactory().make(film.getFilmType());
        BigDecimal price = calculator.getRentalInitialPrice(rentalDTO.getDays(), film.getPriceType());

        return saveRental(rentalDTO.getDays(), film, customer, rental, price);
    }

    public Rental returnRental(Long rentalId) {
        Rental rental = get(rentalId);
        filmService.incrementFilmInventory(rental.getFilm());

        updateSurchargeAndDaysDelayed(rental);

        return rental;
    }

    private void updateSurchargeAndDaysDelayed(Rental rental) {
        int daysDelayed;

        LocalDate expectedReturnDate = rental.getInitialDate().plusDays(rental.getDays());
        if (expectedReturnDate.isBefore(LocalDate.now())) {
            daysDelayed = Period.between(expectedReturnDate, LocalDate.now()).getDays();
            rental.setSurcharges(BigDecimal.valueOf(daysDelayed * 2f));
            rental.setDaysDelayed(daysDelayed);
        }

        rentalRepository.save(rental);
    }

    public Rental get(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new RentalNotFoundException(id));
    }

    public List<Rental> getAll() {
        return rentalRepository.findAll();
    }

    private Rental saveRental(int days, Film film, Customer customer, Rental rental, BigDecimal price) {
        rental.setFilm(film);
        rental.setCustomer(customer);
        rental.setPrice(price);
        rental.setDays(days);
        rental.setInitialDate(LocalDate.now());

        rentalRepository.save(rental);

        return rental;
    }
}