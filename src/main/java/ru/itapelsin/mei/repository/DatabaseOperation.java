package ru.itapelsin.mei.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseOperation<T> {
    T apply(Connection connection) throws SQLException;
}