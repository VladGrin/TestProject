package org.vg.test.service;

import org.vg.test.entity.Order;
import org.vg.test.repo.OrderRepo;

import java.util.List;

public class OrderServiceImpl implements OrderService{

    private final OrderRepo orderRepo;

    public OrderServiceImpl(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    public List<Order> getAll() {
        return orderRepo.getAll();
    }

    @Override
    public Order findById(long id) {
        return orderRepo.findById(id);
    }

    @Override
    public void save(Order order) {
        orderRepo.save(order);
    }
}
