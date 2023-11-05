package org.coolorg.service;

import org.coolorg.database.CustomerRepository;
import org.coolorg.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private CustomerService customerService;
    @Mock
    private CustomerRepository repository;

    @BeforeEach
    void run() {
        customerService = new CustomerService(repository);
    }

    @Test
    void getById_present() {

        Mockito.when(repository.getCustomerById(anyInt())).thenReturn(Optional.of(new Customer()));
        Optional<Customer> maybeCustomer = customerService.getById(0);
        assertTrue(maybeCustomer.isPresent());
    }

    @Test
    void getById_empty() {

        Mockito.when(customerService.getById(10)).thenReturn(Optional.empty());
        Optional<Customer> maybeCustomer = customerService.getById(10);
        assertTrue(maybeCustomer.isEmpty());
    }

    @Test
    void createCustomer() {

        customerService.createCustomer(new Customer(13, "Albina"));
        Mockito.verify(repository, Mockito.times(1))
                .addCustomer(new Customer(13, "Albina"));
    }

    @Test
    void createExistCustomer() {

        Mockito.when(repository.getCustomerById(11)).
                thenReturn(Optional.of(new Customer(11, "Elina")));
        assertThrows(IllegalArgumentException.class, () ->
                customerService.createCustomer(new Customer(11, "Elina")));
    }

    @Test
    void removeCustomer() {

        Mockito.when(repository.getCustomerById(13)).thenReturn(Optional.of(new Customer()));
        customerService.removeCustomer(13);
        Mockito.verify(repository, Mockito.times(1)).removeCustomer(13);

    }

    @Test
    void removeNonExistCustomer() {

        Mockito.when(repository.getCustomerById(27)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> customerService.removeCustomer(27));
    }

}
