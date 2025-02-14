package com.nthmaxfinder.controller;

import com.nthmaxfinder.service.NthMaxService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Nth Max Finder", description = "API для поиска N-го максимального числа в файле XLSX")
public class NthMaxController {

    private final NthMaxService nthMaxService;

    public NthMaxController(NthMaxService nthMaxService) {
        this.nthMaxService = nthMaxService;
    }

    @GetMapping("/nth-max")
    @Operation(summary = "Получить N-е максимальное число",
            description = "Принимает путь к файлу и число N, возвращает N-е максимальное число")
    public int getNthMax(
            @Parameter(description = "Путь к файлу XLSX") @RequestParam String filePath,
            @Parameter(description = "Какое по счету максимальное число искать") @RequestParam int n) {
        return nthMaxService.findNthMax(filePath, n);
    }
}
