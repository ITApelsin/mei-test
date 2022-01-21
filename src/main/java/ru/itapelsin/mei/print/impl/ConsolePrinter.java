package ru.itapelsin.mei.print.impl;

import ru.itapelsin.mei.model.Consumption;
import ru.itapelsin.mei.model.TotalCortege;
import ru.itapelsin.mei.print.Printer;

import java.time.format.DateTimeFormatter;

public class ConsolePrinter implements Printer {

    private static final String FORMAT = "%4s | %10s | %10s | %5s | %5s | %5s | %5s |  %8s | %8s | %8s | %8s |";

    @Override
    public void printConsumptions(Iterable<Consumption> consumptions) {
        System.out.println("consumptions:");
        printHeader();
        for (var consumption : consumptions) {
            System.out.printf((FORMAT) + "%n",
                    consumption.getId(),
                    consumption.getCompany(),
                    DateTimeFormatter.ISO_DATE.format(consumption.getConsumptionDate()),
                    consumption.getFactQliqData1(),
                    consumption.getFactQliqData2(),
                    consumption.getFactQoilData1(),
                    consumption.getFactQoilData2(),
                    consumption.getForecastQliqData1(),
                    consumption.getForecastQliqData2(),
                    consumption.getForecastQoilData1(),
                    consumption.getForecastQoilData2());
        }
    }

    @Override
    public void printTotalCorteges(Iterable<TotalCortege> totalCorteges) {
        System.out.println("total");
        printHeader();
        for (var totalCortege : totalCorteges) {
            System.out.printf((FORMAT) + "%n",
                    "",
                    "",
                    DateTimeFormatter.ISO_DATE.format(totalCortege.getConsumptionDate()),
                    totalCortege.getFactQliqData1(),
                    totalCortege.getFactQliqData2(),
                    totalCortege.getFactQoilData1(),
                    totalCortege.getFactQoilData2(),
                    totalCortege.getForecastQliqData1(),
                    totalCortege.getForecastQliqData2(),
                    totalCortege.getForecastQoilData1(),
                    totalCortege.getForecastQoilData2());
        }
    }


    private void printHeader() {
        System.out.printf((FORMAT) + "%n",
                "id",
                "company",
                "date",
                "fact",
                "fact",
                "fact",
                "fact",
                "forecast",
                "forecast",
                "forecast",
                "forecast");
        System.out.printf((FORMAT) + "%n",
                "",
                "",
                "",
                "Qliq",
                "Qliq",
                "Qoil",
                "Qoil",
                "Qliq",
                "Qliq",
                "Qoil",
                "Qoil");
        System.out.printf((FORMAT) + "%n",
                "",
                "",
                "",
                "data1",
                "data2",
                "data1",
                "data2",
                "data1",
                "data2",
                "data1",
                "data2");
    }
}
