package com.singular.renting.controller;

import com.singular.renting.domain.Film;
import com.singular.renting.exception.FilmNotFoundException;
import com.singular.renting.repository.FilmRepository;
import com.singular.renting.resource.FilmAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class FilmController {

    private final FilmRepository repository;
    private final FilmAssembler assembler;

    FilmController(FilmRepository repository, FilmAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/films")
    public CollectionModel<EntityModel<Film>> all() {
        List<EntityModel<Film>> films = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(films,
                linkTo(methodOn(FilmController.class).all()).withSelfRel());
    }

    @GetMapping("/film")
    public EntityModel<Film> one(Long id) {
        Film film = repository.findById(id)
                .orElseThrow(() -> new FilmNotFoundException(id));
        return assembler.toModel(film);
    }
}
