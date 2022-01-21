package ru.itapelsin.mei.repository.impl;

import ru.itapelsin.mei.repository.DatabaseOperation;
import ru.itapelsin.mei.repository.SqlConnectionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Function;

public class DataSourceSqlConnectionManager implements SqlConnectionManager {

    private final DataSource dataSource;

    public DataSourceSqlConnectionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public <T, E extends Exception> T withConnection(DatabaseOperation<T> function, Function<Exception, E> exceptionConverter) throws E {
        try (var connection = dataSource.getConnection()) {
            return function.apply(connection);
        } catch (SQLException ex) {
            throw exceptionConverter.apply(ex);
        }
    }

    @Override
    public void close() throws Exception {
    }
}
