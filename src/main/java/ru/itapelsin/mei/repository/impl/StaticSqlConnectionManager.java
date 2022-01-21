package ru.itapelsin.mei.repository.impl;

import ru.itapelsin.mei.repository.DatabaseOperation;
import ru.itapelsin.mei.repository.SqlConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Function;

public class StaticSqlConnectionManager implements SqlConnectionManager {

    private Connection connection = null;

    public StaticSqlConnectionManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T, E extends Exception> T withConnection(DatabaseOperation<T> function, Function<Exception, E> exceptionConverter) throws E {
        try {
            return function.apply(connection);
        } catch (SQLException ex) {
            throw exceptionConverter.apply(ex);
        }
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
