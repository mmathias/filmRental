package com.singular.renting.resource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.singular.renting.controller.CustomerController;
import com.singular.renting.controller.FilmController;
import com.singular.renting.controller.RentalController;
import com.singular.renting.domain.Rental;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class RentalAssembler implements RepresentationModelAssembler<Rental, EntityModel<Rental>> {

    @Override
    public EntityModel<Rental> toModel(Rental rental) {
        return new EntityModel<>(
                rental,
                linkTo(methodOn(RentalController.class).getRental(rental.getId())).withSelfRel(),
                linkTo(methodOn(FilmController.class).getFilm(rental.getFilm().getId())).withRel("film"),
                linkTo(methodOn(CustomerController.class).getCustomer(rental.getCustomer().getId())).withRel("customer"));
    }
}
