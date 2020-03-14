package com.singular.renting.config;

import com.singular.renting.domain.Customer;
import com.singular.renting.domain.Film;
import com.singular.renting.domain.FilmType;
import com.singular.renting.domain.PriceType;
import com.singular.renting.repository.CustomerRepository;
import com.singular.renting.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class LoadData implements ApplicationRunner {

    private CustomerRepository customerRepository;
    private FilmRepository filmRepository;

    @Autowired
    public LoadData(CustomerRepository customerRepository, FilmRepository filmRepository) {
        this.customerRepository = customerRepository;
        this.filmRepository = filmRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        Film film = new Film();
        film.setPriceType(PriceType.PREMIUM);
        film.setFilmType(FilmType.NEW_RELEASE);
        film.setQuantity(10);
        filmRepository.save(film);
        Customer customer = new Customer();
        customer.setBonusPoints(20);
        customerRepository.save(customer);
    }
}