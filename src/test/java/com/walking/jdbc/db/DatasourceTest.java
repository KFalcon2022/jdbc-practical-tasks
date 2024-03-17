package com.walking.jdbc.db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatasourceTest {

    private static final String URL = "test";
    private static final String USERNAME = "test";
    private static final String PASSWORD = "test";

    private final MockedStatic<DriverManager> driverManagerMockedStatic = mockStatic(DriverManager.class);

    private final Datasource datasource = new Datasource(URL, USERNAME, PASSWORD);

    @AfterEach
    void tearDown() {
        driverManagerMockedStatic.close();
    }

    @Test
    void getConnection_success() {
        driverManagerMockedStatic.when(() -> DriverManager.getConnection(URL, USERNAME, PASSWORD))
                .thenReturn(mock(Connection.class));

        Executable executable = datasource::getConnection;

        assertDoesNotThrow(executable);
    }

    @Test
    void getConnection_failed() {
        driverManagerMockedStatic.when(() -> DriverManager.getConnection(URL, USERNAME, PASSWORD))
                .thenThrow(SQLException.class);

        Executable executable = datasource::getConnection;

        assertThrows(RuntimeException.class, executable);
    }
}