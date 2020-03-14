package com.singular.renting.resource;

import com.singular.renting.controller.CustomerController;
import com.singular.renting.domain.Customer;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerAssembler implements RepresentationModelAssembler<Customer, EntityModel<Customer>> {

    @Override
    public EntityModel<Customer> toModel(Customer customer) {
        return new EntityModel<>(customer,
                linkTo(methodOn(CustomerController.class).getCustomer(customer.getId())).withSelfRel(),
                linkTo(methodOn(CustomerController.class).getCustomers()).withRel("customers"));
    }
}
