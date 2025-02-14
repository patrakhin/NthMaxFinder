package com.nthmaxfinder.service;

import com.nthmaxfinder.exception.FileProcessingException;
import com.nthmaxfinder.exception.InvalidInputException;
import org.springframework.stereotype.Service;

import java.util.PriorityQueue;
import java.util.List;

@Service
public class NthMaxService {

    private final ExcelParser excelParser;

    public NthMaxService(ExcelParser excelParser) {
        this.excelParser = excelParser;
    }

    public int findNthMax(String filePath, int n) {
        if (n <= 0) {
            throw new InvalidInputException("N должно быть больше 0");
        }

        List<Integer> numbers = excelParser.parseExcelFile(filePath);

        if (numbers.size() < n) {
            throw new InvalidInputException("Файл содержит меньше " + n + " чисел");
        }

        // Используем Min Heap (куча)
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(n);

        for (int num : numbers) {
            if (minHeap.size() < n) {
                minHeap.add(num);
            } else if (num > minHeap.peek()) {
                minHeap.poll();
                minHeap.add(num);
            }
        }

        return minHeap.peek();
    }
}