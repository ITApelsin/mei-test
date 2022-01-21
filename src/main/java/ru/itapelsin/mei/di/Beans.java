package ru.itapelsin.mei.di;

import org.sqlite.SQLiteDataSource;
import ru.itapelsin.mei.parser.ExcelParser;
import ru.itapelsin.mei.parser.impl.ApachePoiExcelParser;
import ru.itapelsin.mei.print.Printer;
import ru.itapelsin.mei.print.impl.ConsolePrinter;
import ru.itapelsin.mei.repository.ConsumptionRepository;
import ru.itapelsin.mei.repository.impl.ConsumptionRepositoryImpl;
import ru.itapelsin.mei.repository.impl.DataSourceSqlConnectionManager;
import ru.itapelsin.mei.repository.SqlConnectionManager;
import ru.itapelsin.mei.repository.impl.StaticSqlConnectionManager;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class Beans implements AutoCloseable {

    private static Beans instance;

    private final ConsumptionRepository consumptionRepository;
    private final ExcelParser parser;
    private final Printer printer;

    private Beans(Profile profile) {
        SqlConnectionManager connectionManager;
        switch (profile) {
            case RELEASE:
                var dataSource = new SQLiteDataSource();
                dataSource.setUrl("jdbc:sqlite:mydb.sqlite");
                connectionManager = new DataSourceSqlConnectionManager(dataSource);
                break;
            case TEST:
                try {
                    connectionManager = new StaticSqlConnectionManager(DriverManager.getConnection("jdbc:sqlite::memory:"));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            default:
                throw new RuntimeException("unsupported profile");
        }
        this.consumptionRepository = new ConsumptionRepositoryImpl(connectionManager);
        consumptionRepository.init();
        this.parser = new ApachePoiExcelParser();
        this.printer = new ConsolePrinter();
    }

    public static Beans getInstance() {
        if (instance == null) {
            try (var is  = Beans.class.getResource("/config.properties").openStream()) {
                var properties = new Properties();
                properties.load(is);
                Profile profile;
                switch (properties.getProperty("profile")) {
                    case "release":
                        profile = Profile.RELEASE;
                        break;
                    case "test":
                        profile = Profile.TEST;
                        break;
                    default:
                        throw new RuntimeException("unsupported profile");
                }
                instance = new Beans(profile);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return instance;
    }

    @Override
    public void close() throws Exception {
        consumptionRepository.close();
        instance = null;
    }

    public ConsumptionRepository getConsumptionRepository() {
        return consumptionRepository;
    }

    public ExcelParser getParser() {
        return parser;
    }

    public Printer getPrinter() {
        return printer;
    }

    private enum Profile {
        TEST, RELEASE
    }
}
