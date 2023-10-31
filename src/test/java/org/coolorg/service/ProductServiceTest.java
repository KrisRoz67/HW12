package org.coolorg.service;

import org.coolorg.database.ProductRepository;
import org.coolorg.model.Product;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {
    private final ProductService p = new ProductService(new ProductRepository());

    @Test
    void getById() {
        assertEquals(Optional.empty(),p.getById(100));
        assertEquals("Product 5", p.getById(5).get().getName());

    }

    @Test
    void createProduct() {
        assertThrows(IllegalArgumentException.class, () ->
                p.createProduct(new Product(1,"Product1",5.5)));
        p.createProduct(new Product(13,"Product 13", 2.20));
        assertEquals(2.20, p.getById(13).get().getPrice());

    }

    @Test
    void getProductPrice() {
       assertEquals(5.50,p.getProductPrice(2));
       assertThrows(IllegalArgumentException.class,() -> p.getProductPrice(23));
    }

    @Test
    void removeProduct() {

        assertThrows(IllegalArgumentException.class,() -> p.removeProduct(111));
        p.removeProduct(2);
        assertEquals(Optional.empty(),p.getById(2));

    }
}