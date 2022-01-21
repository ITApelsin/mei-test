package ru.itapelsin.mei.model;

import java.time.LocalDate;
import java.util.Objects;

public class TotalCortege {

    private LocalDate consumptionDate;

    private double factQliqData1;

    private double factQliqData2;

    private double factQoilData1;

    private double factQoilData2;

    private double forecastQliqData1;

    private double forecastQliqData2;

    private double forecastQoilData1;

    private double forecastQoilData2;

    public LocalDate getConsumptionDate() {
        return consumptionDate;
    }

    public void setConsumptionDate(LocalDate consumptionDate) {
        this.consumptionDate = consumptionDate;
    }

    public double getFactQliqData1() {
        return factQliqData1;
    }

    public void setFactQliqData1(double factQliqData1) {
        this.factQliqData1 = factQliqData1;
    }

    public double getFactQliqData2() {
        return factQliqData2;
    }

    public void setFactQliqData2(double factQliqData2) {
        this.factQliqData2 = factQliqData2;
    }

    public double getFactQoilData1() {
        return factQoilData1;
    }

    public void setFactQoilData1(double factQoilData1) {
        this.factQoilData1 = factQoilData1;
    }

    public double getFactQoilData2() {
        return factQoilData2;
    }

    public void setFactQoilData2(double factQoilData2) {
        this.factQoilData2 = factQoilData2;
    }

    public double getForecastQliqData1() {
        return forecastQliqData1;
    }

    public void setForecastQliqData1(double forecastQliqData1) {
        this.forecastQliqData1 = forecastQliqData1;
    }

    public double getForecastQliqData2() {
        return forecastQliqData2;
    }

    public void setForecastQliqData2(double forecastQliqData2) {
        this.forecastQliqData2 = forecastQliqData2;
    }

    public double getForecastQoilData1() {
        return forecastQoilData1;
    }

    public void setForecastQoilData1(double forecastQoilData1) {
        this.forecastQoilData1 = forecastQoilData1;
    }

    public double getForecastQoilData2() {
        return forecastQoilData2;
    }

    public void setForecastQoilData2(double forecastQoilData2) {
        this.forecastQoilData2 = forecastQoilData2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TotalCortege)) return false;
        TotalCortege that = (TotalCortege) o;
        return Double.compare(that.getFactQliqData1(), getFactQliqData1()) == 0 && Double.compare(that.getFactQliqData2(), getFactQliqData2()) == 0 && Double.compare(that.getFactQoilData1(), getFactQoilData1()) == 0 && Double.compare(that.getFactQoilData2(), getFactQoilData2()) == 0 && Double.compare(that.getForecastQliqData1(), getForecastQliqData1()) == 0 && Double.compare(that.getForecastQliqData2(), getForecastQliqData2()) == 0 && Double.compare(that.getForecastQoilData1(), getForecastQoilData1()) == 0 && Double.compare(that.getForecastQoilData2(), getForecastQoilData2()) == 0 && Objects.equals(getConsumptionDate(), that.getConsumptionDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getConsumptionDate(), getFactQliqData1(), getFactQliqData2(), getFactQoilData1(), getFactQoilData2(), getForecastQliqData1(), getForecastQliqData2(), getForecastQoilData1(), getForecastQoilData2());
    }

    @Override
    public String toString() {
        return "TotalCortege{" +
                "consumptionDate=" + consumptionDate +
                ", factQliqData1=" + factQliqData1 +
                ", factQliqData2=" + factQliqData2 +
                ", factQoilData1=" + factQoilData1 +
                ", factQoilData2=" + factQoilData2 +
                ", forecastQliqData1=" + forecastQliqData1 +
                ", forecastQliqData2=" + forecastQliqData2 +
                ", forecastQoilData1=" + forecastQoilData1 +
                ", forecastQoilData2=" + forecastQoilData2 +
                '}';
    }
}
