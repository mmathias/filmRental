package com.singular.renting.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.singular.renting.domain.Customer;
import com.singular.renting.exception.CustomerNotFoundException;
import com.singular.renting.repository.CustomerRepository;
import com.singular.renting.resource.CustomerAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CustomerController {

    private final CustomerRepository repository;
    private final CustomerAssembler assembler;

    CustomerController(CustomerRepository repository, CustomerAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/customers")
    public CollectionModel<EntityModel<Customer>> all() {
        List<EntityModel<Customer>> customers = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(customers,
                linkTo(methodOn(CustomerController.class).all()).withSelfRel());
    }

    @GetMapping("/customer")
    public EntityModel<Customer> one(Long id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        return assembler.toModel(customer);
    }
}
