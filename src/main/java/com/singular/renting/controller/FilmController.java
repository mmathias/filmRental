package com.singular.renting.controller;

import com.singular.renting.domain.Film;
import com.singular.renting.resource.FilmAssembler;
import com.singular.renting.service.FilmService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "films")
public class FilmController {

    private final FilmAssembler assembler;
    private FilmService service;

    public FilmController(FilmService service, FilmAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Film>> getFilms() {
        List<EntityModel<Film>> films = service.getAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(films,
                linkTo(methodOn(FilmController.class).getFilms()).withSelfRel());
    }

    @GetMapping(path = "/{id}")
    public EntityModel<Film> getFilm(@PathVariable Long id) {
        Film film = service.get(id);

        return assembler.toModel(film);
    }
}
