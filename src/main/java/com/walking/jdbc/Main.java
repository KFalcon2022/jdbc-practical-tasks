package com.walking.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    private final static Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args)  {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/",
                "postgres",
                "postgres")) {
            // Дальнейшая работа с БД через connection
        } catch (SQLException e) {
            log.error(e);
        }
    }
}