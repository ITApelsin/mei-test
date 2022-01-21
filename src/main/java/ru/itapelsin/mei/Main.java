package ru.itapelsin.mei;

import ru.itapelsin.mei.di.Beans;
import ru.itapelsin.mei.repository.exception.RepositoryException;

public class Main {

    public static void main(String ... args) throws Exception {
        if (args.length == 0) {
            throw new RuntimeException("specify file name");
        }
        try (var beans = Beans.getInstance()) {
            var parsedConsumption = beans.getParser().parse(args[0]);
            for (var consumption : parsedConsumption) {
                try {
                    Beans.getInstance().getConsumptionRepository().save(consumption);
                } catch (RepositoryException ex) {
                    System.out.println("save ignore: " + ex.getMessage());
                }
            }
            Beans.getInstance().getPrinter().printConsumptions(Beans.getInstance().getConsumptionRepository().findAll());
            Beans.getInstance().getPrinter().printTotalCorteges(Beans.getInstance().getConsumptionRepository().getTotal());
        }
    }
}
