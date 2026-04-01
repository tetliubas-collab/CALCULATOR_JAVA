package com.calculator.controller;

import com.calculator.model.CalculationRequest;
import com.calculator.model.CalculationResult;
import com.calculator.service.CalculatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("/")
    public String index() {
        return "calculator";
    }

    @PostMapping("/api/calculate")
    @ResponseBody
    public ResponseEntity<CalculationResult> calculate(@RequestBody CalculationRequest request) {
        CalculationResult result = calculatorService.calculate(request);
        return ResponseEntity.ok(result);
    }
}
