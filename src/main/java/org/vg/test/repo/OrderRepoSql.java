package org.vg.test.repo;

import org.vg.test.config.DatabaseConnection;
import org.vg.test.entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepoSql implements OrderRepo {

    @Override
    public List<Order> getAll() {
        String sql = "SELECT ORDER_ID, ORDER_NAME, PRICE FROM Orders";
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

//    @Override
//    public Book createBook(Book book) {
//        String query = "INSERT INTO Books (title, publishedYear, genre, author_id) VALUES (?, ?, ?, ?)";
//        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
//             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//
//            stmt.setString(1, book.getTitle());
//            stmt.setInt(2, book.getPublishedYear());
//            stmt.setString(3, book.getGenre());
//            stmt.setInt(4, book.getAuthorId());
//            stmt.executeUpdate();
//
//            // Отримання згенерованого ID
//            ResultSet generatedKeys = stmt.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                int id = generatedKeys.getInt(1);
//                book.setId(id); // Заповнюємо ID у об'єкті
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return book;
//    }

    @Override
    public Order save(Order order) {
        String sql = "INSERT INTO Orders (ORDER_NAME, PRICE) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            statement.setString(1, order.getOrderName());
            statement.setInt(2, order.getPrice());

            int rowsAffected = statement.executeUpdate();
            System.out.println("Rows inserted: " + rowsAffected);

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long orderId = generatedKeys.getLong(1);
                order.setOrderId(orderId);
            }
            return order;
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
