package org.coolorg.service;

import org.coolorg.database.CustomerRepository;
import org.coolorg.model.Customer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private CustomerService sut;
    @Mock
    private CustomerRepository repository;

    @BeforeEach
    void run() {
        sut = new CustomerService(repository);
    }

    @Test
    void getById_present() {

        Mockito.when(repository.getCustomerById(anyInt())).thenReturn(Optional.of(new Customer()));
        Optional<Customer> maybeCustomer = sut.getById(0);
        assertTrue(maybeCustomer.isPresent());
    }

    @Test
    void getById_empty() {

        Mockito.when(sut.getById(10)).thenReturn(Optional.empty());
        Optional<Customer> maybeCustomer = sut.getById(10);
        assertTrue(maybeCustomer.isEmpty());
    }

    @Test
    void createCustomer() {

        sut.createCustomer(new Customer(13, "Albina"));
        Mockito.verify(repository, Mockito.times(1))
                .addCustomer(new Customer(13, "Albina"));
    }

    @Test
    void createExistCustomer() {

        Mockito.when(repository.getCustomerById(11)).
                thenReturn(Optional.of(new Customer(11, "Elina")));
        assertThrows(IllegalArgumentException.class, () ->
                sut.createCustomer(new Customer(11, "Elina")));
    }

    @Test
    void removeCustomer() {

        Mockito.when(repository.getCustomerById(13)).thenReturn(Optional.of(new Customer()));
        sut.removeCustomer(13);
        Mockito.verify(repository, Mockito.times(1)).removeCustomer(13);

    }

    @Test
    void removeNonExistCustomer() {

        Mockito.when(repository.getCustomerById(27)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> sut.removeCustomer(27));
    }

}
