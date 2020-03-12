package com.singular.renting.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.singular.renting.domain.Rental;
import com.singular.renting.dto.RentalDTO;
import com.singular.renting.resource.RentalAssembler;
import com.singular.renting.service.RentalService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "rentals")
public class RentalController {

    private final RentalAssembler assembler;
    private final RentalService rentalService;

    public RentalController(RentalAssembler assembler, RentalService rentalService) {
        this.assembler = assembler;
        this.rentalService = rentalService;
    }

    @GetMapping("/{id}")
    public EntityModel<Rental> one(@PathVariable Long id) {
        Rental rental = rentalService.get(id);

        return assembler.toModel(rental);
    }

    @GetMapping
    public CollectionModel<EntityModel<Rental>> all() {
        List<EntityModel<Rental>> rentals = rentalService.getAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(rentals,
                linkTo(methodOn(RentalController.class).all()).withSelfRel());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Rental>> newRental(@RequestBody RentalDTO rentalDTO) {
        Rental rental = rentalService.rent(rentalDTO);

        return ResponseEntity
                .created(linkTo(methodOn(RentalController.class).one(rental.getId())).toUri())
                .body(assembler.toModel(rental));
    }

    @PostMapping("/return/{rentalId}")
    // pra devolver algo vc cria algo novo? usar HTTP DELETE nao seria mais intuitivo aqui?
    public ResponseEntity<EntityModel<Rental>> returnRental(@PathVariable Long rentalId) {
        Rental rental = rentalService.returnRental(rentalId);

        return ResponseEntity
                .created(linkTo(methodOn(RentalController.class).one(rentalId)).toUri())
                .body(assembler.toModel(rental));
    }
}
