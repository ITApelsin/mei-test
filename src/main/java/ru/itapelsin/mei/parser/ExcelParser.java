package ru.itapelsin.mei.parser;

import ru.itapelsin.mei.model.Consumption;

import java.io.IOException;
import java.util.List;

public interface ExcelParser {
    List<Consumption> parse(String file) throws IOException;
}
