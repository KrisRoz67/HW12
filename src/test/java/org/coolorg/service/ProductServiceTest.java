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
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void run() {
        productService = new ProductService(productRepository);
    }


    @Test
    void getById() {
        Mockito.when(productRepository.getProductById(12)).thenReturn(Optional.of(new Product()));
        Optional<Product> maybeProduct = productService.getById(12);
        assertTrue( maybeProduct.isPresent());
    }

    @Test
    void getById_empty() {
        Mockito.when(productRepository.getProductById(120)).thenReturn(Optional.empty());
        Optional<Product> maybeProduct= productService.getById(120);
        assertTrue(maybeProduct.isEmpty());

    }

    @Test
    void createProduct() {
        productService.createProduct(new Product(1, "Product 1", 1));
        Mockito.verify(productRepository, Mockito.times(1)).addProduct(new Product(1, "Product 1", 1));
    }

    @Test
    void createExistProduct() {
        Mockito.when(productRepository.getProductById(16)).thenReturn(Optional.of(new Product(16, "Product 16", 16)));
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(new Product(16, "Product 16", 16)));
    }

    @Test
    void getProductPrice() {
        Mockito.when(productRepository.getProductById(6)).thenReturn(Optional.of(new Product(6, "Product 6", 7)));
        double price = productService.getProductPrice(6);
        assertEquals(7, price);
    }

    @Test
    void removeProduct() {

        Mockito.when(productRepository.getProductById(12)).thenReturn(Optional.of(new Product()));
        productService.removeProduct(12);
        Mockito.verify(productRepository, Mockito.times(1)).removeProduct(12);
    }

    @Test
    void removeNonExistProduct() {
        Mockito.when(productRepository.getProductById(11)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> productService.removeProduct(11));
    }
}