package ru.itapelsin.mei.repository;

import ru.itapelsin.mei.model.TotalCortege;
import ru.itapelsin.mei.model.Consumption;
import ru.itapelsin.mei.repository.exception.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface ConsumptionRepository extends AutoCloseable {
    void init() throws RepositoryException;
    void clear() throws RepositoryException;
    List<TotalCortege> getTotal() throws RepositoryException;
    Optional<Consumption> findById(Long id) throws RepositoryException;
    List<Consumption> findAll() throws RepositoryException;
    void save(Consumption entity) throws RepositoryException;

    @Override
    default void close() throws Exception { }
}
