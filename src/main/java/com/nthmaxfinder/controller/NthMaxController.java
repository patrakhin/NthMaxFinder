package com.nthmaxfinder.controller;

import com.nthmaxfinder.service.NthMaxService;
import com.nthmaxfinder.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;

@RestController
@RequestMapping("/api")
@Tag(name = "Nth Max Finder", description = "API для поиска N-го максимального числа в файле XLSX")
public class NthMaxController {

    private final NthMaxService nthMaxService;

    public NthMaxController(NthMaxService nthMaxService) {
        this.nthMaxService = nthMaxService;
    }

    @GetMapping("/nth-max")
    @Operation(
            summary = "Получить N-е максимальное число",
            description = "Принимает путь к файлу и число N, возвращает N-е максимальное число",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешный ответ",
                            content = @Content(schema = @Schema(type = "integer"))),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            }
    )
    public ResponseEntity<Object> getNthMax(
            @Parameter(description = "Путь к файлу XLSX", example = "/path/to/file.xlsx")
            @RequestParam String filePath,

            @Parameter(description = "Какое по счету максимальное число искать", example = "3")
            @RequestParam String n) {

        try {
            // Преобразуем строку в целое число
            int number = Integer.parseInt(n);

            // Логика поиска N-го максимального числа
            BigInteger result = nthMaxService.findNthMax(filePath, number);
            return ResponseEntity.ok(result);

        } catch (NumberFormatException ex) {
            // В случае ошибки преобразования числа
            ErrorResponse errorResponse = new ErrorResponse("Некорректный ввод данных");
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception ex) {
            // Обработка других возможных ошибок
            ErrorResponse errorResponse = new ErrorResponse("Ошибка сервера");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}