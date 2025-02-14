package com.nthmaxfinder.util;

import com.nthmaxfinder.exception.FileProcessingException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelParser {

    public List<BigInteger> parseExcelFile(String filePath) {
        List<BigInteger> numbers = new ArrayList<>();

        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new FileProcessingException("Файл не найден: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cell = row.getCell(0);

                // Если ячейка содержит число (типа NUMERIC)
                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                    double numericValue = cell.getNumericCellValue();
                    BigDecimal bigDecimalValue = BigDecimal.valueOf(numericValue);
                    numbers.add(bigDecimalValue.toBigInteger()); // Преобразуем в BigInteger
                }
                // Если ячейка содержит строку
                if (cell != null && cell.getCellType() == CellType.STRING) {
                    String stringValue = cell.getStringCellValue();
                    numbers.add(parseBigInteger(stringValue)); // Преобразуем строку в BigInteger
                }
            }

        } catch (IOException e) {
            throw new FileProcessingException("Ошибка при обработке файла: " + filePath);
        }

        return numbers;
    }

    // Метод для обработки строки в BigInteger
    private BigInteger parseBigInteger(String stringValue) {
        try {
            return new BigInteger(stringValue); // Преобразуем строку в BigInteger
        } catch (NumberFormatException e) {
            throw new FileProcessingException("Некорректное число в ячейке: " + stringValue);
        }
    }
}
