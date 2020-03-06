package com.singular.renting.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.singular.renting.domain.Rental;
import com.singular.renting.dto.RentalDTO;
import com.singular.renting.resource.RentalAssembler;
import com.singular.renting.service.RentalService;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RentalController {

    private final RentalAssembler assembler;
    private final RentalService rentalService;

    public RentalController(RentalAssembler assembler, RentalService rentalService) {
        this.assembler = assembler;
        this.rentalService = rentalService;
    }

    @GetMapping("/rental")
    public EntityModel<Rental> one(Long id) {
        Rental rental = rentalService.getRental(id);

        return assembler.toModel(rental);
    }

    @PostMapping("/rental")
    public ResponseEntity<EntityModel<Rental>> newRental (@RequestBody RentalDTO rentalDTO) {
        Rental rental = rentalService.rent(rentalDTO);

        return ResponseEntity
                .created(linkTo(methodOn(RentalController.class).one(rental.getId())).toUri())
                .body(assembler.toModel(rental));
    }

    @PostMapping("/return")
    public ResponseEntity<EntityModel<Rental>> returnRental (@RequestBody RentalDTO rentalDTO) {

        return null;
    }
}
