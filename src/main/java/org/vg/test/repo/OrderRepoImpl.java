package org.vg.test.repo;

import org.vg.test.entity.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderRepoImpl implements OrderRepo {

    private final Map<Long, Order> orders = new HashMap<>();

    @Override
    public List<Order> getAll() {
        return new ArrayList<>(orders.values());
    }

    @Override
    public Order findById(long id) {
        return orders.get(id);
    }

    @Override
    public void save(Order order) {
        orders.put(order.getOrderId(), order);
    }
}
