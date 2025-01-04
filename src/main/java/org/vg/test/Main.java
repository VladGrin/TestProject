package org.vg.test;

import org.vg.test.entity.Order;
import org.vg.test.repo.OrderRepo;
import org.vg.test.repo.OrderRepoSql;
import org.vg.test.service.OrderService;
import org.vg.test.service.OrderServiceImpl;

public class Main {
    public static void main(String[] args) {

        OrderRepo orderRepo = new OrderRepoSql();
        OrderService orderService = new OrderServiceImpl(orderRepo);

        orderService.save(new Order(0, "Fourth", 6000));
        System.out.println("-----------------");
        for (Order order : orderService.getAll()) {
            System.out.println(order);
        }

    }
}
