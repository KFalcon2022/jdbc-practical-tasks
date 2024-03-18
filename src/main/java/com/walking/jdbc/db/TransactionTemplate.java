package com.walking.jdbc.db;

import java.sql.Connection;
import java.util.function.Function;

public class TransactionTemplate {

    private final Datasource datasource;

    public TransactionTemplate(Datasource datasource) {
        this.datasource = datasource;
    }

    public <T> T runTransactional(Function<Connection, T> function) {
        try (var connection = datasource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                T result = function.apply(connection);

                connection.commit();

                return result;
            } catch (Exception e) {
                connection.rollback();
                throw e;
            }

        } catch (Exception e) {
            if (e instanceof RuntimeException runtimeException) {
                throw runtimeException;
            }

            throw new RuntimeException("Ошибка при обработке транзакции", e);
        }
    }
}
