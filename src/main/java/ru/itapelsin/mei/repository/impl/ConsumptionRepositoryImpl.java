package ru.itapelsin.mei.repository.impl;

import ru.itapelsin.mei.model.Consumption;
import ru.itapelsin.mei.model.TotalCortege;
import ru.itapelsin.mei.repository.ConsumptionRepository;
import ru.itapelsin.mei.repository.SqlConnectionManager;
import ru.itapelsin.mei.repository.exception.RepositoryException;
import ru.itapelsin.mei.utils.ModelOrmUtils;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class ConsumptionRepositoryImpl implements ConsumptionRepository {

    private final SqlConnectionManager connectionManager;

    public ConsumptionRepositoryImpl(SqlConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public List<TotalCortege> getTotal() {
        return connectionManager.withConnection(connection -> {
            try (var statement = connection.createStatement()) {
                var resultSet = statement.executeQuery("" +
                        "SELECT consumption_date, " +
                        "SUM(fact_Qliq_data1) AS total_fact_Qliq_data1, " +
                        "SUM(fact_Qliq_data2) AS total_fact_Qliq_data2, " +
                        "SUM(fact_Qoil_data1) AS total_fact_Qoil_data1, " +
                        "SUM(fact_Qoil_data2) AS total_fact_Qoil_data2 , " +
                        "SUM(forecast_Qliq_data1) AS total_forecast_Qliq_data1, " +
                        "SUM(forecast_Qliq_data2) AS total_forecast_Qliq_data2, " +
                        "SUM(forecast_Qoil_data1) AS total_forecast_Qoil_data1, " +
                        "SUM(forecast_Qoil_data2) AS total_forecast_Qoil_data2 " +
                        "FROM Consumption " +
                        "GROUP BY consumption_date " +
                        "ORDER BY consumption_date ASC");
                var buffer = new LinkedList<TotalCortege>();
                while (resultSet.next()) {
                    buffer.add(ModelOrmUtils.fillTotalCortege(new TotalCortege(), resultSet));
                }
                return buffer;
            }
        }, RepositoryException::new);
    }

    @Override
    public Optional<Consumption> findById(Long id) {
        return connectionManager.withConnection(connection -> {
            try (var statement = connection.prepareStatement("SELECT * FROM Consumption WHERE id = ?")) {
                statement.setLong(1, requireNonNull(id));
                var resultSet = statement.executeQuery();
                return resultSet.next() ? Optional.of(ModelOrmUtils.fillConsumption(new Consumption(), resultSet)) : Optional.empty();
            }
        }, RepositoryException::new);
    }

    @Override
    public List<Consumption> findAll() {
        return connectionManager.withConnection(connection -> {
            try (var statement = connection.createStatement()) {
                var resultSet = statement.executeQuery("SELECT * FROM Consumption ORDER BY id ASC");
                var buffer = new LinkedList<Consumption>();
                while (resultSet.next()) {
                    buffer.add(ModelOrmUtils.fillConsumption(new Consumption(), resultSet));
                }
                return buffer;
            }
        }, RepositoryException::new);
    }

    @Override
    public void save(Consumption entity) {
        requireNonNull(entity);
        connectionManager.withConnection(connection -> {
            try (var statement= connection.prepareStatement(
                    "INSERT INTO Consumption VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

                statement.setLong(1, requireNonNull(entity.getId()));
                statement.setString(2, requireNonNull(entity.getCompany()));
                statement.setDate(3, Date.valueOf(requireNonNull(entity.getConsumptionDate())));
                statement.setDouble(4, requireNonNull(entity.getFactQliqData1()));
                statement.setDouble(5, requireNonNull(entity.getFactQliqData2()));
                statement.setDouble(6, requireNonNull(entity.getFactQoilData1()));
                statement.setDouble(7, requireNonNull(entity.getFactQoilData2()));
                statement.setDouble(8, requireNonNull(entity.getForecastQliqData1()));
                statement.setDouble(9, requireNonNull(entity.getForecastQliqData2()));
                statement.setDouble(10, requireNonNull(entity.getForecastQoilData1()));
                statement.setDouble(11, requireNonNull(entity.getForecastQoilData2()));

                statement.executeUpdate();
            }
            return null;
        }, RepositoryException::new);
    }

    @Override
    public void clear() {
        connectionManager.withConnection(connection -> {
            try (var statement = connection.createStatement()) {
                statement.executeUpdate("DELETE FROM Consumption");
            }
            return null;
        }, RepositoryException::new);
    }

    @Override
    public void close() throws Exception {
        connectionManager.close();
    }

    @Override
    public void init() throws RepositoryException {
        connectionManager.withConnection(connection -> {
            try (var statement = connection.createStatement()) {
                statement.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS Consumption (" +
                                "id INTEGER NOT NULL PRIMARY KEY, " +
                                "company VARCHAR(20) NOT NULL, " +
                                "consumption_date date NOT NULL, " +
                                "fact_Qliq_data1 DOUBLE NOT NULL, " +
                                "fact_Qliq_data2 DOUBLE NOT NULL, " +
                                "fact_Qoil_data1 DOUBLE NOT NULL, " +
                                "fact_Qoil_data2 DOUBLE NOT NULL, " +
                                "forecast_Qliq_data1 DOUBLE NOT NULL, " +
                                "forecast_Qliq_data2 DOUBLE NOT NULL, " +
                                "forecast_Qoil_data1 DOUBLE NOT NULL, " +
                                "forecast_Qoil_data2 DOUBLE NOT NULL)");
            }
            return null;
        }, RepositoryException::new);
    }
}
