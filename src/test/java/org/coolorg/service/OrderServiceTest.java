package org.coolorg.service;

import org.coolorg.database.CustomerRepository;
import org.coolorg.database.OrderRepository;
import org.coolorg.database.ProductRepository;
import org.coolorg.model.Order;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    private final OrderService o = new OrderService(new OrderRepository(),
           // new CustomerService(new CustomerRepository()),
            new ProductService(new ProductRepository()));

    @Test
    void getOrderById() {
        {
            Optional<Order> order1 = o.getOrderById(1);
            assertEquals(1, order1.get().getCustomerId());
        }
        {
            Optional<Order> order1 = o.getOrderById(1);
            assertEquals(1, order1.get().getProductId());
        }
        {
            assertEquals(Optional.empty(), o.getOrderById(118));
        }
    }


    @Test
    void getOrdersByCustomer() {
        {
            List<Integer> customerOrders = o.getOrdersByCustomer(2)
                    .stream().map(Order::getProductId).toList();
            List<Integer> expected = List.of(3, 2, 1);
            assertEquals(expected, customerOrders);

        }
        {
            List<Integer> customerOrders = o.getOrdersByCustomer(9)
                    .stream().map(Order::getProductId).toList();
            List<Integer> expected = List.of(4);
            assertEquals(expected, customerOrders);
        }

    }

    @Test
    void getTotalPriceForCustomer() {
        {
            assertEquals(3.0, o.getTotalPriceForCustomer(9));
        }
        {
            assertEquals(0.0, o.getTotalPriceForCustomer(3));
        }
    }

    @Test
    void createOrder() {
        {
            o.createOrder(new Order(12, 2, 1));
            Optional<Order> order1 = o.getOrderById(12);
            assertEquals(2, order1.get().getCustomerId());
        }

        {
            assertThrows(IllegalArgumentException.class, () ->
                    o.createOrder(new Order(1, 1, 1)));
        }

    }

    @Test
    void removeOrder() {
        {
            o.removeOrder(2);
            assertEquals(Optional.empty(), o.getOrderById(2));
        }
        {
            assertThrows(IllegalArgumentException.class, () ->
                    o.removeOrder(1233));
        }
    }
}