package org.coolorg.service;

import org.coolorg.database.ProductRepository;
import org.coolorg.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    private ProductService p;

    @Mock
    private ProductRepository productRep;

    @BeforeEach
    void run() {
        p = new ProductService(productRep);
    }


    @Test
    void getById() {
        Mockito.when(productRep.getProductById(12)).thenReturn(Optional.of(new Product()));
        Optional<Product> maybeProduct = p.getById(12);
        assertNotEquals(Optional.empty(), maybeProduct);
    }

    @Test
    void getById_empty() {
        Mockito.when(productRep.getProductById(120)).thenReturn(Optional.empty());
        Optional<Product> maybeProduct= p.getById(120);
        assertEquals(Optional.empty(), maybeProduct);

    }

    @Test
    void createProduct() {
        p.createProduct(new Product(1, "Product 1", 1));
        Mockito.verify(productRep, Mockito.times(1)).addProduct(new Product(1, "Product 1", 1));
    }

    @Test
    void createExistProduct() {
        Mockito.when(productRep.getProductById(16)).thenReturn(Optional.of(new Product(16, "Product 16", 16)));
        assertThrows(IllegalArgumentException.class, () -> p.createProduct(new Product(16, "Product 16", 16)));
    }

    @Test
    void getProductPrice() {
        Mockito.when(productRep.getProductById(6)).thenReturn(Optional.of(new Product(6, "Product 6", 7)));
        double price = p.getProductPrice(6);
        assertEquals(7, price);
    }

    @Test
    void removeProduct() {

        Mockito.when(productRep.getProductById(12)).thenReturn(Optional.of(new Product()));
        p.removeProduct(12);
        Mockito.verify(productRep, Mockito.times(1)).removeProduct(12);
    }

    @Test
    void removeNonExistProduct() {
        Mockito.when(productRep.getProductById(11)).thenReturn(Optional.empty());
        Optional<Product> product1 = p.getById(11);
        assertThrows(IllegalArgumentException.class, () -> p.removeProduct(11));
    }
}