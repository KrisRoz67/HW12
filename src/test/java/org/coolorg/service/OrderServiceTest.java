package org.coolorg.service;

import org.coolorg.database.OrderRepository;
import org.coolorg.model.Order;
import org.coolorg.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    private OrderService o;

    @Mock
    private ProductService productService;
    @Mock
    private OrderRepository orderRep;


    @BeforeEach
    void run() {
        o = new OrderService(orderRep, productService);
    }


    @Test
    void getOrderById() {

        Mockito.when(orderRep.getOrderById(anyInt())).thenReturn(Optional.of(new Order()));
        Optional<Order> maybeOrder = orderRep.getOrderById(10);
        assertNotEquals(Optional.empty(), maybeOrder );
    }

    @Test
    void getById_empty() {

        Mockito.when(orderRep.getOrderById(203)).thenReturn(Optional.empty());
        Optional<Order>  maybeOrder  = o.getOrderById(203);
        assertEquals(Optional.empty(),  maybeOrder );
    }


    @Test
    void getOrdersByCustomer() {

        List<Order> e = List.of(new Order(1, 2, 3), new Order(2, 2, 3));
        Mockito.when(orderRep.getOrdersByCustomer(12)).
                thenReturn(e);
        assertEquals(2, o.getOrdersByCustomer(12).size());

    }


    @Test
    void getTotalPriceForCustomer() {

        List<Order> orders = List.of(new Order(1, 1, 1), new Order(2, 1, 1));
        Mockito.when(orderRep.getOrdersByCustomer(1)).
                thenReturn(orders);
        Mockito.when(productService.getById(1)).
                thenReturn(Optional.of(new Product(1, "Product 1", 3.0)));
        double sum = o.getTotalPriceForCustomer(1);
        assertEquals(6.0, sum);

    }

    @Test
    void createExistedOrder() {

        Mockito.when(orderRep.getOrderById(10)).
                thenReturn(Optional.of(new Order(10, 1, 1)));
        assertThrows(IllegalArgumentException.class, () ->
                o.createOrder(new Order(10, 1, 1)));
    }

    @Test
    void createOrder() {

        o.createOrder(new Order(10, 1, 1));
        Mockito.verify(orderRep, Mockito.times(1))
                .addOrder(new Order(10, 1, 1));
    }


    @Test
    void removeOrder() {

        Mockito.when(orderRep.getOrderById(12)).thenReturn(Optional.of(new Order()));
        o.removeOrder(12);
        Mockito.verify(orderRep, Mockito.times(1)).removeOrder(12);

    }

    @Test
    void removeNonExistOrder() {

        Mockito.when(orderRep.getOrderById(11)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> o.removeOrder(11));

    }
}