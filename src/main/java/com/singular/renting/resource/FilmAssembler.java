package com.singular.renting.resource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.singular.renting.controller.FilmController;
import com.singular.renting.domain.Film;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class FilmAssembler implements RepresentationModelAssembler<Film, EntityModel<Film>> {

    @Override
    public EntityModel<Film> toModel(Film film) {
        return new EntityModel<Film>(film,
                linkTo(methodOn(FilmController.class).getFilm(film.getId())).withSelfRel(),
                linkTo(methodOn(FilmController.class).getFilms()).withRel("films"));
    }
}
