package com.walking.jdbc;

import com.walking.jdbc.mapper.PassengerMapper;
import com.walking.jdbc.repository.PassengerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * Задача урока 129:
 * <a href="https://github.com/KFalcon2022/lessons/blob/master/lessons/jdbc/129/Statement.%20DDL.%20ResultSet.md#%D0%B7%D0%B0%D0%B4%D0%B0%D1%87%D0%B0">ссылка</a>
 * <p>
 * Задача урока 130:
 * <a href="https://github.com/KFalcon2022/lessons/blob/master/lessons/jdbc/130/PreparedStatement.%20SQL%20injection.md#%D0%B7%D0%B0%D0%B4%D0%B0%D1%87%D0%B0">ссылка</a>
 */
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

    private void executeParameterizedQueryWithPreparedStatementExample() {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from passenger where id = ?")) {

            // ... - Подготовка данных для выполнения запроса

            ResultSet result = statement.executeQuery();

            // ... - Дальнейшая обработка ResultSet
        } catch (SQLException e) {
            log.error(e);
        }
    }

    private void executeStaticQueryWithPreparedStatementExample() {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from passenger")) {

            ResultSet result = statement.executeQuery();

            // ... - Дальнейшая обработка ResultSet
        } catch (SQLException e) {
            log.error(e);
        }
    }

    private void findPassengersByFullNameExample() {
        var passengerMapper = new PassengerMapper();
        var passengerRepository = new PassengerRepository(passengerMapper);

        passengerRepository.findByFullName("Ivan", "Ivanov");
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/test_db",
                "postgres",
                "postgres");
    }
}