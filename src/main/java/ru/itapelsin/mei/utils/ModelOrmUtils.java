package ru.itapelsin.mei.utils;

import ru.itapelsin.mei.model.TotalCortege;
import ru.itapelsin.mei.model.Consumption;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ModelOrmUtils {

    private ModelOrmUtils() { }

    public static Consumption fillConsumption(Consumption consumption, ResultSet resultSet) throws SQLException {
        consumption.setId(resultSet.getLong("id"));
        consumption.setCompany(resultSet.getString("company"));
        consumption.setConsumptionDate(resultSet.getDate("consumption_date").toLocalDate());
        consumption.setFactQliqData1(resultSet.getDouble("fact_Qliq_data1"));
        consumption.setFactQliqData2(resultSet.getDouble("fact_Qliq_data2"));
        consumption.setFactQoilData1(resultSet.getDouble("fact_Qoil_data1"));
        consumption.setFactQoilData2(resultSet.getDouble("fact_Qoil_data2"));
        consumption.setForecastQliqData1(resultSet.getDouble("forecast_Qliq_data1"));
        consumption.setForecastQliqData2(resultSet.getDouble("forecast_Qliq_data2"));
        consumption.setForecastQoilData1(resultSet.getDouble("forecast_Qoil_data1"));
        consumption.setForecastQoilData2(resultSet.getDouble("forecast_Qoil_data2"));
        return consumption;
    }

    public static TotalCortege fillTotalCortege(TotalCortege totalCortege, ResultSet resultSet) throws SQLException {
        totalCortege.setConsumptionDate(resultSet.getDate("consumption_date").toLocalDate());
        totalCortege.setFactQliqData1(resultSet.getDouble("total_fact_Qliq_data1"));
        totalCortege.setFactQliqData2(resultSet.getDouble("total_fact_Qliq_data2"));
        totalCortege.setFactQoilData1(resultSet.getDouble("total_fact_Qoil_data1"));
        totalCortege.setFactQoilData2(resultSet.getDouble("total_fact_Qoil_data2"));
        totalCortege.setForecastQliqData1(resultSet.getDouble("total_forecast_Qliq_data1"));
        totalCortege.setForecastQliqData2(resultSet.getDouble("total_forecast_Qliq_data2"));
        totalCortege.setForecastQoilData1(resultSet.getDouble("total_forecast_Qoil_data1"));
        totalCortege.setForecastQoilData2(resultSet.getDouble("total_forecast_Qoil_data2"));
        return totalCortege;
    }
}
