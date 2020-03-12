package com.singular.renting.service;

import com.singular.renting.domain.Film;
import com.singular.renting.exception.FilmNotFoundException;
import com.singular.renting.repository.FilmRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FilmServiceTest {

    @Mock
    FilmRepository filmRepository;

    @InjectMocks
    FilmService service;

    private static final Long FILM_ID = 1L;

    @Test
    public void itShouldReturnAllFilms() {
        Film film = new Film();
        List<Film> films = Collections.singletonList(film);
        when(filmRepository.findAll()).thenReturn(films);

        List<Film> filmsOutput = service.getAll();

        assertEquals(films, filmsOutput);
    }

    @Test
    public void itShouldReturnOneFilm() {
        Film film = new Film();
        when(filmRepository.findById(FILM_ID)).thenReturn(java.util.Optional.of(film));

        Film filmOutput = service.get(FILM_ID);

        assertEquals(film, filmOutput);
    }

    @Test
    public void itShouldThrowExceptionWhenFilmDoesntExist() {
        FilmNotFoundException exception = assertThrows(
                FilmNotFoundException.class, () -> service.get(FILM_ID));

        assertEquals("Couldn't find film " + FILM_ID, exception.getMessage());
    }
}
