package org.vg.test.repo;

import org.vg.test.entity.Order;

import java.util.List;

public interface OrderRepo {

    List<Order> getAll();

    Order findById(long id);

    Order save(Order order);

    boolean update(Order order);

    boolean delete(Order order);
}
