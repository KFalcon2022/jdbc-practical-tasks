package com.walking.jdbc;

import com.walking.jdbc.mapper.PassengerMapper;
import com.walking.jdbc.repository.PassengerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class Main {
    private final static Logger log = LogManager.getLogger(Main.class);

    private void createConnectionExample() {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/test_db",
                "postgres",
                "postgres")) {
            // Дальнейшая работа с БД через connection
        } catch (SQLException e) {
            log.error(e);
        }
    }

    private void createStatementExample() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            // Дальнейшая работа с БД через statement
        } catch (SQLException e) {
            log.error(e);
        }
    }

    private void executeDDLExample() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            var sql = """
                    create table table_example (
                        column_example  bigint
                    )
                    """;

            statement.execute(sql);
        } catch (SQLException e) {
            log.error(e);
        }
    }

    private void executeSelectQueryExample() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

//            Отправка SELECT-запроса через Statement
            statement.execute("select * from passenger");

//            Получение результатов SELECT-запроса.
//            Данный ResultSet будет содержать все строки и столбцы таблицы passenger.
            ResultSet result = statement.getResultSet();

//            ... - Дальнейшая обработка ResultSet
        } catch (SQLException e) {
            log.error(e);
        }
    }

    private void executeQueryExample() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

//            Отправка SELECT-запроса через Statement
            ResultSet result = statement.executeQuery("select * from passenger");

//            ... - Дальнейшая обработка ResultSet
        } catch (SQLException e) {
            log.error(e);
        }
    }

    private void executeUpdateExample() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            var sql = """
                    create table table_example (
                        column_example  bigint
                    )
                    """;

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            log.error(e);
        }
    }

    private void findAllPassengersExample() {
        var passengerMapper = new PassengerMapper();
        var passengerRepository = new PassengerRepository(passengerMapper);

        passengerRepository.findAll();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/test_db",
                "postgres",
                "postgres");
    }
}