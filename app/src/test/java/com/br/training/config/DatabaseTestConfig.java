package com.br.training.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DatabaseTestConfig {

    private static final String URL = "jdbc:postgresql://localhost:5432/novaaurora";
    private static final String USER = "teste";
    private static final String PASSWORD = "teste123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initializeDatabase() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            String schema = new String(Files.readAllBytes(Paths.get("src/test/resources/schema.sql")));
            statement.execute(schema);

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }
}