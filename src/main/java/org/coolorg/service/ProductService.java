package org.coolorg.service;

import lombok.RequiredArgsConstructor;
import org.coolorg.database.ProductRepository;
import org.coolorg.model.Product;

import java.util.Optional;

@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Получить продукт по его уникальному идентификатору.
     *
     * @param id Уникальный идентификатор продукта.
     * @return {@link Optional}, содержащий продукт, если найден, или пустой {@link Optional}, если не найден.
     */
    public Optional<Product> getById(final int id) {
        return productRepository.getProductById(id);
    }

    /**
     * Создать новый продукт и добавить его в репозиторий.
     *
     * @param product Продукт, который нужно создать и добавить.
     * @throws IllegalArgumentException Если продукт с таким идентификатором уже существует в репозитории.
     */
    public void createProduct(final Product product) {
        Optional<Product> product1 = getById(product.getId());
        if (product1.isPresent()) {
            throw new IllegalArgumentException("Product with this id already exist");
        } else {
            productRepository.addProduct(product);
        }

    }

    public double getProductPrice(int productId) {
        if (getById(productId).isPresent()) {
            Product product = getById(productId).get();
            return product.getPrice();
        } else {
            throw new IllegalArgumentException("Price can't be found");
        }
    }


    /**
     * Удалить продукт по его уникальному идентификатору.
     *
     * @param id Уникальный идентификатор продукта, который нужно удалить.
     * @throws IllegalArgumentException Если продукт с указанным идентификатором не существует в репозитории.
     */
    public void removeProduct(final int id) {
        Optional<Product> producById = getById(id);
        if (producById.isEmpty()) {
            throw new IllegalArgumentException("Product with this id doesn't exist");
        } else {
            productRepository.removeProduct(id);
        }

    }
}
