package org.vg.test.repo;

import org.vg.test.config.DatabaseConnection;
import org.vg.test.entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepoSql implements OrderRepo {

    @Override
    public List<Order> getAll() {
        String sql = "SELECT ORDER_ID, ORDER_NAME, PRICE FROM car_box.Orders";
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();) {

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                long id = resultSet.getLong("ORDER_ID");
                String name = resultSet.getString("ORDER_NAME");
                int price = resultSet.getInt("PRICE");
                Order order = new Order(id, name, price);
                orders.add(order);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    @Override
    public Order findById(long id) {
        String sql = "SELECT * FROM Orders WHERE ORDER_ID = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("ORDER_NAME");
                int price = resultSet.getInt("PRICE");
                return new Order(id, name, price);
            } else {
                System.out.println("Order not found by id=" + id);
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean save(Order order) {
        String sql = "INSERT INTO Orders (ORDER_NAME, PRICE) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setString(1, order.getOrderName());
            statement.setInt(2, order.getPrice());

            int rowsAffected = statement.executeUpdate();

            System.out.println("Rows inserted: " + rowsAffected);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Order order) {
        Order orderById = findById(order.getOrderId());
        if (orderById == null) {
            return false;
        }
        String sql = "UPDATE Orders SET ORDER_NAME=?, PRICE=? WHERE ORDER_ID = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setString(1, order.getOrderName());
            statement.setInt(2, order.getPrice());
            statement.setLong(3, order.getOrderId());

            int rows = statement.executeUpdate(sql);
            return rows == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Order order) {
        Order orderById = findById(order.getOrderId());
        if (orderById == null) {
            return false;
        }
        String sql = "DELETE FROM Orders WHERE ORDER_ID = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setLong(1, order.getOrderId());

            return statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
