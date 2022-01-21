package ru.itapelsin.mei.repository;

import java.util.function.Function;

public interface SqlConnectionManager extends AutoCloseable {
    <T, E extends Exception> T withConnection(DatabaseOperation<T> function, Function<Exception, E> exceptionConverter) throws E;
}
