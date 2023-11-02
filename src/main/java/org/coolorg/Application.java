package org.coolorg;

import org.coolorg.database.CustomerRepository;
import org.coolorg.database.OrderRepository;
import org.coolorg.database.ProductRepository;
import org.coolorg.model.Order;
import org.coolorg.service.CustomerService;
import org.coolorg.service.OrderService;
import org.coolorg.service.ProductService;
import java.util.List;
import java.util.Optional;


public class Application {
    public static void main(String[] args) {
        final CustomerService c = new CustomerService(new CustomerRepository());
        final OrderService o = new OrderService(new OrderRepository(),
                new CustomerService(new CustomerRepository()),
                new ProductService(new ProductRepository()));

        final ProductService p = new ProductService(new ProductRepository());


        System.out.printf("Total sum paid by customer with id 1 is %s\n", o.getTotalPriceForCustomer(1));
        if (c.getById(13).isPresent()) {
            System.out.printf("Client name of customer with id 8 is %s\n", c.getById(2).get().getName());

        }else {
            System.out.println("No such customer");
        }
        List<Integer> productsList = o.getOrdersByCustomer(2).stream().map(Order::getProductId).toList();
        List<String> str = productsList.stream()
                .map(p::getById)
                .filter(Optional::isPresent)
                .map(product -> product.get().getName())
                .toList();
        System.out.printf("Client ordered  %s", str);
        c.removeCustomer(2);
    }

}
