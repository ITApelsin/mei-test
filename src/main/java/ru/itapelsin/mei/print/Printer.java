package ru.itapelsin.mei.print;

import ru.itapelsin.mei.model.Consumption;
import ru.itapelsin.mei.model.TotalCortege;

public interface Printer {
    void printConsumptions(Iterable<Consumption> consumptions);
    void printTotalCorteges(Iterable<TotalCortege> totalCorteges);
}
