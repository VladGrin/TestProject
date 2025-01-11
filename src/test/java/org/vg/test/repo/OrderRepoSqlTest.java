package org.vg.test.repo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.vg.test.config.DatabaseConnection;
import org.vg.test.entity.Order;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class OrderRepoSqlTest {

    // F    Fast
    // I    Independent
    // R    Repeatable
    // S    Self-validating
    // T    Timely

    // TDD Test Driven Development

    private static final Order ORDER = new Order();

    private OrderRepo orderRepo = new OrderRepoSql();

    @Before
    public void init() {
        try {
            DatabaseConnection.getInstance().initializeDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void clean() {

    }

    @Test
    public void getAll() {
        // init
        // call
        // check

        final List<Order> all = orderRepo.getAll();

        assertEquals(1, all.size());
        assertTrue(all.size() == 1);
        assertFalse(all.isEmpty());
    }

    @Test
    public void findById() {
        Order expectOrder = new Order(1, "First", 1000);

        Order orderRepoById = orderRepo.findById(1);

        assertEquals(expectOrder, orderRepoById);
    }

    @Test
    public void save() {
        Order order = new Order();
        order.setOrderName("Second");
        order.setPrice(2000);

        Order savedOrder = orderRepo.save(order);

        assertEquals(2, savedOrder.getOrderId());
        assertEquals("Second", savedOrder.getOrderName());
        assertEquals(2000, savedOrder.getPrice());

        final List<Order> all = orderRepo.getAll();
        System.out.println("");
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}