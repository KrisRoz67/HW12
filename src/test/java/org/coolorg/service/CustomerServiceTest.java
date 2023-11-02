package org.coolorg.service;
import org.coolorg.database.CustomerRepository;
import org.coolorg.model.Customer;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {
    private final CustomerService sut = new CustomerService(new CustomerRepository());

    @Test
    void getById() {
        {
            Optional<Customer> res = sut.getById(1);
            assertEquals("Bob", res.get().getName());
        }
        {
            Optional<Customer> res2 = sut.getById(11);
            assertEquals(Optional.empty(), res2);
        }
        {
            Optional<Customer> res1 = sut.getById(10);
            assertEquals("Kate", res1.get().getName());
        }

    }

    @Test
    void createCustomer() {
        {
            sut.createCustomer(new Customer(11, "Monica"));
            Optional<Customer> customer1 = sut.getById(11);
            assertEquals("Monica", customer1.get().getName());
        }
        {
            sut.createCustomer(new Customer(12, "Adam"));
            Optional<Customer> customer1 = sut.getById(12);
            assertEquals("Adam", customer1.get().getName());
        }
        {
            assertThrows(IllegalArgumentException.class, () ->
                    sut.createCustomer(new Customer(10, "Monica")));
        }
        {
            assertThrows(IllegalArgumentException.class, () ->
                    sut.createCustomer(new Customer(5, "Monica")));
        }
    }

    @Test
    void removeCustomer() {
        {
            sut.createCustomer(new Customer(13, "Maria"));
            sut.removeCustomer(13);
            assertEquals(Optional.empty(), sut.getById(11));
        }
        {
            sut.removeCustomer(2);
            assertEquals(Optional.empty(), sut.getById(11));
        }
        {
            assertThrows(IllegalArgumentException.class, () -> sut.removeCustomer(27));
        }

    }
}