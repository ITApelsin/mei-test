package ru.itpaelsin.mei;

import org.junit.jupiter.api.Test;
import ru.itapelsin.mei.model.Consumption;
import ru.itapelsin.mei.model.TotalCortege;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class TestUtils {

    private TestUtils() { }

    public static Consumption getTestConsumption() {
        var testConsumption = new Consumption();
        testConsumption.setId(1L);
        testConsumption.setCompany("company1");
        testConsumption.setConsumptionDate(LocalDate.now());
        testConsumption.setFactQliqData1(1.0);
        testConsumption.setFactQliqData2(2.0);
        testConsumption.setFactQoilData1(3.0);
        testConsumption.setFactQoilData2(4.0);
        testConsumption.setForecastQliqData1(5.0);
        testConsumption.setForecastQliqData2(6.0);
        testConsumption.setForecastQoilData1(7.0);
        testConsumption.setForecastQoilData2(8.0);
        return testConsumption;
    }

    public static TotalCortege getTestTotalCortege() {
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
        return testTotalCortege;
    }

    public static List<Consumption> generateRandomConsumptions(int count) {
        var buffer = new ArrayList<Consumption>();
        for (int i = 0; i < count; i++) {
            var randomizer = new Random();
            var consumption = new Consumption();

            consumption.setId((long) i);
            consumption.setCompany("randomCompany");
            consumption.setConsumptionDate(LocalDate.now());
            consumption.setFactQliqData1(1.0 + (100.0 - 1.0) * randomizer.nextDouble());
            consumption.setFactQliqData2(1.0 + (100.0 - 1.0) * randomizer.nextDouble());
            consumption.setFactQoilData1(1.0 + (100.0 - 1.0) * randomizer.nextDouble());
            consumption.setFactQoilData2(1.0 + (100.0 - 1.0) * randomizer.nextDouble());
            consumption.setForecastQliqData1(1.0 + (100.0 - 1.0) * randomizer.nextDouble());
            consumption.setForecastQliqData2(1.0 + (100.0 - 1.0) * randomizer.nextDouble());
            consumption.setForecastQoilData1(1.0 + (100.0 - 1.0) * randomizer.nextDouble());
            consumption.setForecastQoilData2(1.0 + (100.0 - 1.0) * randomizer.nextDouble());

            buffer.add(consumption);
        }
        return buffer;
    }
}
