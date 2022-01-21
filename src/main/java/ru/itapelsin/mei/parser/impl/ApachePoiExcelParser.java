package ru.itapelsin.mei.parser.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.itapelsin.mei.model.Consumption;
import ru.itapelsin.mei.parser.ExcelParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public class ApachePoiExcelParser implements ExcelParser {

    private static final Map<String, WorkbookLoader> FORMAT_WORKBOOK_MAP = Map.of(
            "xls", file -> {
                try (var is = new FileInputStream(file)) {
                    return new HSSFWorkbook(is);
                }
            },
            "xlsx", file -> {
                try (var is = new FileInputStream(file)) {
                    return new XSSFWorkbook(is);
                }
            });

    private static final Map<String, BiConsumer<Cell, Consumption>> FIELD_FILL_COMMAND_MAP;

    static {
        FIELD_FILL_COMMAND_MAP = new HashMap<>();
        FIELD_FILL_COMMAND_MAP.put("id", (cell, consumption) -> consumption.setId((long) cell.getNumericCellValue()));
        FIELD_FILL_COMMAND_MAP.put("company", (cell, consumption) -> consumption.setCompany(cell.getStringCellValue()));
        FIELD_FILL_COMMAND_MAP.put("date", (cell, consumption) -> consumption.setConsumptionDate(cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
        FIELD_FILL_COMMAND_MAP.put("fact_Qliq_data1", (cell, consumption) -> consumption.setFactQliqData1(cell.getNumericCellValue()));
        FIELD_FILL_COMMAND_MAP.put("fact_Qliq_data2", (cell, consumption) -> consumption.setFactQliqData2(cell.getNumericCellValue()));
        FIELD_FILL_COMMAND_MAP.put("fact_Qoil_data1", (cell, consumption) -> consumption.setFactQoilData1(cell.getNumericCellValue()));
        FIELD_FILL_COMMAND_MAP.put("fact_Qoil_data2", (cell, consumption) -> consumption.setFactQoilData1(cell.getNumericCellValue()));
        FIELD_FILL_COMMAND_MAP.put("forecast_Qliq_data1", (cell, consumption) -> consumption.setForecastQliqData1(cell.getNumericCellValue()));
        FIELD_FILL_COMMAND_MAP.put("forecast_Qliq_data2", (cell, consumption) -> consumption.setForecastQliqData2(cell.getNumericCellValue()));
        FIELD_FILL_COMMAND_MAP.put("forecast_Qoil_data1", (cell, consumption) -> consumption.setForecastQoilData1(cell.getNumericCellValue()));
        FIELD_FILL_COMMAND_MAP.put("forecast_Qoil_data2", (cell, consumption) -> consumption.setForecastQoilData1(cell.getNumericCellValue()));
    }

    @Override
    public List<Consumption> parse(String fileName) throws IOException {
        var file = new File(fileName);
        var ext = getExtension(file);
        var workbookLoader = FORMAT_WORKBOOK_MAP.get(ext);
        Objects.requireNonNull(workbookLoader, "unsupported format of file " + ext);
        try (var workbook = workbookLoader.load(file)) {
            var sheet = workbook.getSheetAt(0);
            var headerMap = parseHeader(sheet, "", 0, 0, FIELD_FILL_COMMAND_MAP.size() - 1);
            if (!headerMap.keySet().containsAll(FIELD_FILL_COMMAND_MAP.keySet())) {
                var notFoundFields = FIELD_FILL_COMMAND_MAP.keySet();
                notFoundFields.removeAll(headerMap.keySet());
                throw new RuntimeException("unsupported header. next fields not mapped: " + notFoundFields);
            }
            int row = 3;
            var result = new LinkedList<Consumption>();
            while (sheet.getRow(row) != null) {
                final var parsedConsumption = new Consumption();
                final var currentRow = row++;
                FIELD_FILL_COMMAND_MAP.forEach(
                                (fieldName, consumer) -> consumer.accept(sheet.getRow(currentRow).getCell(headerMap.get(fieldName)),
                                parsedConsumption));
                result.add(parsedConsumption);
            }
            return result;
        }
    }

    private Map<String, Integer> parseHeader(Sheet sheet, String prefix, final int row, final int firstColumn, final int lastColumn) {
        var headerMap = new HashMap<String, Integer>();
        var expectedHeader = FIELD_FILL_COMMAND_MAP.keySet();
        for (int column = firstColumn; column <= lastColumn; column++) {
            var cell = sheet.getRow(row).getCell(column);
            String cellValue = prefix + cell.getStringCellValue();
            if (expectedHeader.contains(cellValue)) {
                headerMap.put(cellValue, column);
            } else {
                final var currentColumn = column;
                var mergedRegionOpt = sheet.getMergedRegions().stream()
                        .filter(region -> region.containsRow(row) && region.containsColumn(currentColumn))
                        .findFirst();
                if (mergedRegionOpt.isPresent()) {
                    var mergedRegion = mergedRegionOpt.get();
                    var subgroupHeader = parseHeader(sheet, cellValue + "_", row + 1,
                            column, mergedRegion.getLastColumn());
                    column = subgroupHeader.values().stream().max(Integer::compareTo).orElse(mergedRegion.getLastColumn());
                    headerMap.putAll(subgroupHeader);
                } else {
                    break;
                }
            }
        }
        return headerMap;
    }

    private String getExtension(File file) {
        var name = file.getName();
        int lastIndex = name.lastIndexOf('.');
        return lastIndex != -1 ? name.substring(lastIndex + 1) : "";
    }

    private interface WorkbookLoader {
        Workbook load(File file) throws IOException;
    }
}
