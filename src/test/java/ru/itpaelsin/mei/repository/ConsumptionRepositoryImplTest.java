package ru.itpaelsin.mei.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.itapelsin.mei.di.Beans;
import ru.itapelsin.mei.model.TotalCortege;
import ru.itpaelsin.mei.TestUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ConsumptionRepositoryImplTest {

    @BeforeAll
    public static void beforeAll() throws Exception {
        Beans.getInstance();
    }

    @AfterAll
    public static void afterAll() throws Exception {
        Beans.getInstance().close();
    }

    @BeforeEach
    public void beforeEach() {
        Beans.getInstance().getConsumptionRepository().clear();
    }

    @Test
    public void testSaveLoad() {
        var testConsumption = TestUtils.getTestConsumption();
        Beans.getInstance().getConsumptionRepository().save(testConsumption);
        var storedConsumptionOpt = Beans.getInstance().getConsumptionRepository().findById(testConsumption.getId());
        assertFalse(storedConsumptionOpt.isEmpty());
        assertEquals(testConsumption, storedConsumptionOpt.get());
    }

    @Test
    public void testFindAll() {
        var consumptionList = TestUtils.generateRandomConsumptions(5);
        consumptionList.forEach(Beans.getInstance().getConsumptionRepository()::save);
        assertEquals(consumptionList, Beans.getInstance().getConsumptionRepository().findAll());
    }

    @Test
    public void testGetTotal() {
        final var date1 = LocalDate.parse("2022-01-01", DateTimeFormatter.ISO_DATE);
        final var date2 = LocalDate.parse("2022-01-20", DateTimeFormatter.ISO_DATE);

        var expected = new ArrayList<TotalCortege>(2);
        expected.add(new TotalCortege());
        expected.add(new TotalCortege());

        expected.get(0).setConsumptionDate(date1);
        expected.get(1).setConsumptionDate(date2);

        var list = TestUtils.generateRandomConsumptions(5);
        for (int i = 0; i < list.size(); i++) {
            var consumption = list.get(i);
            if (i % 2 != 0) {
                consumption.setConsumptionDate(date1);
                expected.get(0).setFactQliqData1(consumption.getFactQliqData1() + expected.get(0).getFactQliqData1());
                expected.get(0).setFactQliqData2(consumption.getFactQliqData2() + expected.get(0).getFactQliqData2());
                expected.get(0).setFactQoilData1(consumption.getFactQoilData1() + expected.get(0).getFactQoilData1());
                expected.get(0).setFactQoilData2(consumption.getFactQoilData2() + expected.get(0).getFactQoilData2());
                expected.get(0).setForecastQliqData1(consumption.getForecastQliqData1() + expected.get(0).getForecastQliqData1());
                expected.get(0).setForecastQliqData2(consumption.getForecastQliqData2() + expected.get(0).getForecastQliqData2());
                expected.get(0).setForecastQoilData1(consumption.getForecastQoilData1() + expected.get(0).getForecastQoilData1());
                expected.get(0).setForecastQoilData2(consumption.getForecastQoilData2() + expected.get(0).getForecastQoilData2());
            } else {
                consumption.setConsumptionDate(date2);
                expected.get(1).setFactQliqData1(consumption.getFactQliqData1() + expected.get(1).getFactQliqData1());
                expected.get(1).setFactQliqData2(consumption.getFactQliqData2() + expected.get(1).getFactQliqData2());
                expected.get(1).setFactQoilData1(consumption.getFactQoilData1() + expected.get(1).getFactQoilData1());
                expected.get(1).setFactQoilData2(consumption.getFactQoilData2() + expected.get(1).getFactQoilData2());
                expected.get(1).setForecastQliqData1(consumption.getForecastQliqData1() + expected.get(1).getForecastQliqData1());
                expected.get(1).setForecastQliqData2(consumption.getForecastQliqData2() + expected.get(1).getForecastQliqData2());
                expected.get(1).setForecastQoilData1(consumption.getForecastQoilData1() + expected.get(1).getForecastQoilData1());
                expected.get(1).setForecastQoilData2(consumption.getForecastQoilData2() + expected.get(1).getForecastQoilData2());
            }
        }

        list.forEach(Beans.getInstance().getConsumptionRepository()::save);
        assertEquals(expected, Beans.getInstance().getConsumptionRepository().getTotal());
    }
}
