package com.singular.renting.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.singular.renting.domain.Customer;
import com.singular.renting.resource.CustomerAssembler;
import com.singular.renting.service.CustomerService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "customers")
public class CustomerController {

    private final CustomerAssembler assembler;
    private CustomerService service;

    public CustomerController(CustomerService service, CustomerAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    // all? all o que? findAll? updateAll? o principio de Express Intent do Clean Code ficaria legal aqui :)
    public CollectionModel<EntityModel<Customer>> all() {
        List<EntityModel<Customer>> customers = service.getAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(customers,
                linkTo(methodOn(CustomerController.class).all()).withSelfRel());
    }

    @GetMapping(path = "/{id}")
    // one? findOne? updateOne?
    public EntityModel<Customer> one(@PathVariable Long id) {
        Customer customer = service.get(id);

        return assembler.toModel(customer);
    }
}
