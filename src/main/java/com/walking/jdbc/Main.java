package com.walking.jdbc;

import com.walking.jdbc.mapper.PassengerMapper;
import com.walking.jdbc.model.Passenger;
import com.walking.jdbc.repository.PassengerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collection;

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

    private void addBatchStatementExampleExample() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.addBatch("insert into passenger (first_name, last_name, birth_date) values ('Name0', 'Surname0', '1997-12-20')");
            statement.addBatch("insert into passenger (first_name, last_name, birth_date) values ('Name1', 'Surname1', '1997-12-20')");
            statement.addBatch("insert into passenger (first_name, last_name, birth_date) values ('Name2', 'Surname2', '1997-12-20')");
            statement.addBatch("update passenger set first_name = 'IVAN' where id = 1");

//            ... - Выполнение батча
        } catch (SQLException e) {
            log.error(e);
        }
    }

    private void executeBatchStatementExampleExample() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.addBatch("insert into passenger (first_name, last_name, birth_date) values ('Name0', 'Surname0', '1997-12-20')");
            statement.addBatch("insert into passenger (first_name, last_name, birth_date) values ('Name1', 'Surname1', '1997-12-20')");
            statement.addBatch("insert into passenger (first_name, last_name, birth_date) values ('Name2', 'Surname2', '1997-12-20')");
            statement.addBatch("update passenger set first_name = 'IVAN' where id = 1");

            statement.executeBatch();
        } catch (SQLException e) {
            log.error(e);
        }
    }

    private void addBatchPreparedStatementExampleExample(Collection<Passenger> passengers) {
        var sql = "insert into passenger (first_name, last_name, birth_date) values (?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (Passenger passenger : passengers) {
                statement.setString(1, passenger.getFirstName());
                statement.setString(2, passenger.getLastName());

                Date birthDate = Date.valueOf(passenger.getBirthDate());
                statement.setDate(3, birthDate);

                statement.addBatch();
            }

            statement.executeBatch();
        } catch (SQLException e) {
            log.error(e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/test_db",
                "postgres",
                "postgres");
    }
}