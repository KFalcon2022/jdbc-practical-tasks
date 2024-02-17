package com.walking.jdbc.repository;

import com.walking.jdbc.mapper.PassengerMapper;
import com.walking.jdbc.model.Passenger;

import java.sql.*;
import java.util.List;

public class PassengerRepository {

    private final PassengerMapper mapper;

    public PassengerRepository(PassengerMapper mapper) {
        this.mapper = mapper;
    }

    public List<Passenger> findAll() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery("select * from passenger");

            return mapper.map(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении пассажиров", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/test_db",
                "postgres",
                "postgres");
    }
}
