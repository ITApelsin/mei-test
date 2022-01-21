package ru.itpaelsin.mei.utils;

import org.junit.jupiter.api.Test;
import ru.itapelsin.mei.model.Consumption;
import ru.itapelsin.mei.model.TotalCortege;
import ru.itapelsin.mei.utils.ModelOrmUtils;
import ru.itpaelsin.mei.TestUtils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ModelOrmUtilsTest {

    @Test
    public void testFillConsumption() throws SQLException {
        var mockResultSet = mock(ResultSet.class);
        var testConsumption = TestUtils.getTestConsumption();

        when(mockResultSet.getLong("id")).thenReturn(testConsumption.getId());
        when(mockResultSet.getString("company")).thenReturn(testConsumption.getCompany());
        when(mockResultSet.getDate("consumption_date")).thenReturn(Date.valueOf(testConsumption.getConsumptionDate()));
        when(mockResultSet.getDouble("fact_Qliq_data1")).thenReturn(testConsumption.getFactQliqData1());
        when(mockResultSet.getDouble("fact_Qliq_data2")).thenReturn(testConsumption.getFactQliqData2());
        when(mockResultSet.getDouble("fact_Qoil_data1")).thenReturn(testConsumption.getFactQoilData1());
        when(mockResultSet.getDouble("fact_Qoil_data2")).thenReturn(testConsumption.getFactQoilData2());
        when(mockResultSet.getDouble("forecast_Qliq_data1")).thenReturn(testConsumption.getForecastQliqData1());
        when(mockResultSet.getDouble("forecast_Qliq_data2")).thenReturn(testConsumption.getForecastQliqData2());
        when(mockResultSet.getDouble("forecast_Qoil_data1")).thenReturn(testConsumption.getForecastQoilData1());
        when(mockResultSet.getDouble("forecast_Qoil_data2")).thenReturn(testConsumption.getForecastQoilData2());

        assertEquals(
                testConsumption,
                ModelOrmUtils.fillConsumption(new Consumption(), mockResultSet));
    }

    @Test
    public void testFillTotalCortege() throws SQLException {
        var mockResultSet = mock(ResultSet.class);
        var testTotalCortege = new TotalCortege();

        testTotalCortege.setConsumptionDate(LocalDate.now());
        testTotalCortege.setFactQliqData1(1.0);
        testTotalCortege.setFactQliqData2(2.0);
        testTotalCortege.setFactQoilData1(3.0);
        testTotalCortege.setFactQoilData2(4.0);
        testTotalCortege.setForecastQliqData1(5.0);
        testTotalCortege.setForecastQliqData2(6.0);
        testTotalCortege.setForecastQoilData1(7.0);
        testTotalCortege.setForecastQoilData2(8.0);

        when(mockResultSet.getDate("consumption_date")).thenReturn(Date.valueOf(testTotalCortege.getConsumptionDate()));
        when(mockResultSet.getDouble("total_fact_Qliq_data1")).thenReturn(testTotalCortege.getFactQliqData1());
        when(mockResultSet.getDouble("total_fact_Qliq_data2")).thenReturn(testTotalCortege.getFactQliqData2());
        when(mockResultSet.getDouble("total_fact_Qoil_data1")).thenReturn(testTotalCortege.getFactQoilData1());
        when(mockResultSet.getDouble("total_fact_Qoil_data2")).thenReturn(testTotalCortege.getFactQoilData2());
        when(mockResultSet.getDouble("total_forecast_Qliq_data1")).thenReturn(testTotalCortege.getForecastQliqData1());
        when(mockResultSet.getDouble("total_forecast_Qliq_data2")).thenReturn(testTotalCortege.getForecastQliqData2());
        when(mockResultSet.getDouble("total_forecast_Qoil_data1")).thenReturn(testTotalCortege.getForecastQoilData1());
        when(mockResultSet.getDouble("total_forecast_Qoil_data2")).thenReturn(testTotalCortege.getForecastQoilData2());

        assertEquals(
                testTotalCortege.toString(),
                ModelOrmUtils.fillTotalCortege(new TotalCortege(), mockResultSet).toString());
    }
}
