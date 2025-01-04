package org.vg.test.repo;

import org.vg.test.entity.Order;

import java.util.List;

public interface OrderRepo {

    List<Order> getAll();

    Order findById(long id);

    void save(Order order);
}
