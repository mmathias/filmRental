package com.singular.renting.controller;

import com.singular.renting.domain.Customer;
import com.singular.renting.resource.CustomerAssembler;
import com.singular.renting.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Mock
    private CustomerService service;
    @Mock
    private CustomerAssembler assembler;

    @InjectMocks
    private CustomerController controller;

    @Test
    public void itShouldAListOfCustomers() {
        Customer customer = new Customer();
        List<Customer> customers = Collections.singletonList(customer);

        when(service.getAll()).thenReturn(customers);
        when(assembler.toModel(customer)).thenReturn(new EntityModel<>(customer));
        CollectionModel<EntityModel<Customer>> customersOutput = controller.getCustomers();

        verify(service).getAll();
        assertTrue(
                customersOutput
                        .getContent()
                        .stream()
                        .map(EntityModel::getContent)
                        .allMatch(customer1 -> customer == customer1));
    }

    @Test
    public void itShouldReturnACustomerByID() {
        Customer customer = new Customer();
        Long id = 123L;
        when(service.get(id)).thenReturn(customer);
        when(assembler.toModel(customer)).thenReturn(new EntityModel<>(customer));

        EntityModel<Customer> customerEntityModel = controller.getCustomer(id);

        verify(service).get(id);
        assertEquals(customer, customerEntityModel.getContent());
    }
}
