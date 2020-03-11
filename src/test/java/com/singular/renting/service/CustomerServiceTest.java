package com.singular.renting.service;

import com.singular.renting.repository.CustomerRepository;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService service;

    @Test
    public void is() {
    }
}
