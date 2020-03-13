package com.singular.renting.service;

import com.singular.renting.domain.Film;
import com.singular.renting.exception.FilmNotFoundException;
import com.singular.renting.exception.NotEnoughFilmsException;
import com.singular.renting.repository.FilmRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FilmService {

    private FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public Film get(Long id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new FilmNotFoundException(id));
    }

    public List<Film> getAll() {
        return filmRepository.findAll();
    }

    public Film incrementFilmInventory(Film film) {
        increaseQuantity(film);
        filmRepository.save(film);

        return film;
    }

    public Film getAndUpdateInventoryFilm(Long filmId) {
        Film film = get(filmId);
        decreaseQuantity(film);

        filmRepository.save(film);

        return film;
    }

    private void increaseQuantity(Film film) {
        film.setQuantity(film.getQuantity() + 1);
    }

    private void decreaseQuantity(Film film) {
        int quantity = film.getQuantity() - 1;
        if (quantity < 0) throw new NotEnoughFilmsException(film.getId());
        film.setQuantity(quantity);
    }
}
