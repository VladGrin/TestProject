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
    private Connection connection;

    private DatabaseConnection() throws SQLException {
        initProperties();
    }

    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
}
