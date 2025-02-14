package com.nthmaxfinder.util;

import com.nthmaxfinder.exception.FileProcessingException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelParser {

    public List<Integer> parseExcelFile(String filePath) {
        List<Integer> numbers = new ArrayList<>();

        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new FileProcessingException("Файл не найден: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                    numbers.add((int) cell.getNumericCellValue());
                }
            }

        } catch (IOException e) {
            throw new FileProcessingException("Ошибка при обработке файла: " + filePath);
        }

        return numbers;
    }
}
