package com.nthmaxfinder.service;

import com.nthmaxfinder.util.ExcelParser;
import com.nthmaxfinder.exception.InvalidInputException;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.PriorityQueue;
import java.util.List;

@Service
public class NthMaxService {

    private final ExcelParser excelParser;

    public NthMaxService(ExcelParser excelParser) {
        this.excelParser = excelParser;
    }

    public BigInteger findNthMax(String filePath, int n) {
        if (n <= 0) {
            throw new InvalidInputException("N должно быть больше 0");
        }

        List<BigInteger> numbers = excelParser.parseExcelFile(filePath);

        if (numbers.size() < n) {
            throw new InvalidInputException("Файл содержит меньше " + n + " чисел");
        }

        // Используем Min Heap (кучу) для BigInteger
        PriorityQueue<BigInteger> minHeap = new PriorityQueue<>(n);

        for (BigInteger num : numbers) {
            if (minHeap.size() < n) {
                minHeap.add(num);
            } else if (num.compareTo(minHeap.peek()) > 0) {
                minHeap.poll();
                minHeap.add(num);
            }
        }

        // Проверяем, что куча не пуста перед вызовом peek
        if (minHeap.isEmpty()) {
            throw new InvalidInputException("Ошибка: не удалось найти N-е максимальное число");
        }

        return minHeap.peek();
    }
}