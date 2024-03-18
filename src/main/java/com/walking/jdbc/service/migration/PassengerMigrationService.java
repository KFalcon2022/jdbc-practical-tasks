package com.walking.jdbc.service.migration;

import com.walking.jdbc.db.Datasource;

import java.sql.SQLException;

public class PassengerMigrationService implements MigrationService {
    private final Datasource datasource;

    public PassengerMigrationService(Datasource datasource) {
        this.datasource = datasource;
    }

    @Override
    public void migrate() {
        var sql = """
                create table if not exists passenger (
                	id 				bigserial 		primary key,
                	first_name		varchar(100)	not null,
                	last_name		varchar(100)	not null,
                	male			boolean			not null,
                	birth_date		date			not null,
                	last_purchase	timestamp
                );
                """;

        try (var connection = datasource.getConnection();
             var statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при применении миграции", e);
        }
    }
}
