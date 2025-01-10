package org.vg.test.repo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.vg.test.entity.Order;

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

        assertEquals(3, all.size());
        assertTrue(all.size() == 3);
        assertFalse(all.isEmpty());

    }

    @Test
    public void findById() {
        Order expectOrder = new Order(1, "First", 100000);

        Order orderRepoById = orderRepo.findById(1);

        assertEquals(expectOrder, orderRepoById);
    }

    @Test
    public void save() {
        assertEquals(3, 2 + 1);
        assertTrue(5 == 2 + 3);
        assertFalse(5 == 2 + 2);
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}