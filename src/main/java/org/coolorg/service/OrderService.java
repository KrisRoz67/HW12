package org.coolorg.service;

import lombok.RequiredArgsConstructor;
import org.coolorg.database.OrderRepository;
import org.coolorg.model.Order;
import org.coolorg.model.Product;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    /**
     * Получить заказ по его уникальному идентификатору.
     *
     * @param id Уникальный идентификатор заказа.
     * @return {@link Optional}, содержащий заказ, если найден, или пустой {@link Optional}, если не найден.
     */
    public Optional<Order> getOrderById(int id) {

        return orderRepository.getOrderById(id);
    }

    /**
     * Получить список заказов, связанных с конкретным клиентом.
     *
     * @param customerId Уникальный идентификатор клиента.
     * @return Список заказов, связанных с клиентом.
     */
    public List<Order> getOrdersByCustomer(int customerId) {
        return orderRepository.getOrdersByCustomer(customerId);

    }

    /**
     * Рассчитать общую стоимость всех заказов для конкретного клиента.
     *
     * @param customerId Уникальный идентификатор клиента.
     * @return Общая стоимость всех заказов для клиента.
     */
    public double getTotalPriceForCustomer(int customerId) {
        double sum = 0;
        for (Order o : getOrdersByCustomer(customerId)) {
            int productID = o.getProductId();
            if (productService.getById(productID).isPresent()) {
                Product product1 = productService.getById(productID).get();
                sum = sum + product1.getPrice();
            }
        }
        return sum;
    }

    /**
     * Создать новый заказ и добавить его в репозиторий.
     *
     * @param order Заказ, который нужно создать и добавить.
     * @throws IllegalArgumentException Если заказ уже существует в репозитории.
     */
    public void createOrder(Order order) {
        Optional<Order> order1 = getOrderById(order.getId());
        if (order1.isPresent()) {
            throw new IllegalArgumentException("Order with this id already exist");
        } else {
            orderRepository.addOrder(order);
        }
    }

    /**
     * Удалить заказ по его уникальному идентификатору.
     *
     * @param orderId Уникальный идентификатор заказа, который нужно удалить.
     * @throws IllegalArgumentException Если заказ с указанным идентификатором не существует в репозитории.
     */
    public void removeOrder(int orderId) {
        Optional<Order> orderById = getOrderById(orderId);
        if (orderById.isEmpty()) {
            throw new IllegalArgumentException("Order with this id doesn't exist");
        } else {
            orderRepository.removeOrder(orderId);
        }

    }

}
