package org.vg.test.service;

import org.vg.test.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAll();

    Order findById(long id);

    void save(Order order);
}
