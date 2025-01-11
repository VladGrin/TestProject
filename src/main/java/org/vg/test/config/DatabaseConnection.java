package org.vg.test.config;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnection {

    private String url;
    private String user;
    private String password;

    private static DatabaseConnection instance;
    private final Connection connection;

    private DatabaseConnection() throws SQLException {
        initProperties();
        connection = DriverManager.getConnection(url, user, password);
    }

    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null || instance.connection.isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private void initProperties() {
        FileInputStream fis;
        Properties property = new Properties();
        try {

            fis = new FileInputStream("src/main/resources/application.properties");
            property.load(fis);
            this.url = property.getProperty("db.mysql.url");
            this.user = property.getProperty("db.mysql.user");
            this.password = property.getProperty("db.mysql.pwd");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/db.properties"))) {

            StringBuilder sqlBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sqlBuilder.append(line);
                if (line.endsWith(";")) {
                    statement.execute(sqlBuilder.toString());
                    sqlBuilder.setLength(0); // Очищуємо після виконання
                }
            }
        } catch (IOException | RuntimeException | java.sql.SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Не вдалося ініціалізувати базу даних", e);
        }
    }
}
