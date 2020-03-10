package com.singular.renting.service;

import com.singular.renting.domain.Customer;
import com.singular.renting.exception.CustomerNotFoundException;
import com.singular.renting.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer get(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer getCustomerAndCalculateBonusPoints(Long customerId, int bonusPoints) {
        Customer customer = getCustomer(customerId);
        updateCustomerPoints(customer, bonusPoints);

        customerRepository.save(customer);

        return customer;
    }

    private void updateCustomerPoints(Customer customer, int bonusPoints) {
        customer.setBonusPoints(customer.getBonusPoints() + bonusPoints);
    }

    private Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }
}
