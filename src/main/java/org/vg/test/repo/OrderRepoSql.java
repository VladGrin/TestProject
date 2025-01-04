package org.vg.test.repo;

import org.vg.test.entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepoSql implements OrderRepo {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/car_box?useSSL=true&characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASSWORD = "1111";


    @Override
    public List<Order> getAll() {
        String sql = "SELECT ORDER_ID, ORDER_NAME, PRICE FROM car_box.Orders";
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
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
        return null;
    }

    @Override
    public void save(Order order) {
        String sql = "INSERT INTO Orders (ORDER_NAME, PRICE) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setString(1, order.getOrderName());
            statement.setInt(2, order.getPrice());

            int rowsAffected = statement.executeUpdate();

            System.out.println("Rows inserted: " + rowsAffected);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
