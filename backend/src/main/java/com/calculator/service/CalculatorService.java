package com.calculator.service;

import com.calculator.model.CalculationRequest;
import com.calculator.model.CalculationResult;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public CalculationResult calculate(CalculationRequest request) {
        double a = request.getA();
        double b = request.getB();
        String op = request.getOperation();

        return switch (op) {
            case "add"      -> add(a, b);
            case "subtract" -> subtract(a, b);
            case "multiply" -> multiply(a, b);
            case "divide"   -> divide(a, b);
            case "modulo"   -> modulo(a, b);
            case "power"    -> power(a, b);
            case "sqrt"     -> sqrt(a);
            default         -> CalculationResult.ofError("Невідома операція: " + op);
        };
    }

    public CalculationResult add(double a, double b) {
        double result = a + b;
        return new CalculationResult(result, a + " + " + b + " = " + format(result));
    }

    public CalculationResult subtract(double a, double b) {
        double result = a - b;
        return new CalculationResult(result, a + " - " + b + " = " + format(result));
    }

    public CalculationResult multiply(double a, double b) {
        double result = a * b;
        return new CalculationResult(result, a + " × " + b + " = " + format(result));
    }

    public CalculationResult divide(double a, double b) {
        if (b == 0) {
            return CalculationResult.ofError("Ділення на нуль неможливе");
        }
        double result = a / b;
        return new CalculationResult(result, a + " ÷ " + b + " = " + format(result));
    }

    public CalculationResult modulo(double a, double b) {
        if (b == 0) {
            return CalculationResult.ofError("Ділення на нуль неможливе");
        }
        double result = a % b;
        return new CalculationResult(result, a + " % " + b + " = " + format(result));
    }

    public CalculationResult power(double a, double b) {
        double result = Math.pow(a, b);
        return new CalculationResult(result, a + " ^ " + b + " = " + format(result));
    }

    public CalculationResult sqrt(double a) {
        if (a < 0) {
            return CalculationResult.ofError("Квадратний корінь з від'ємного числа неможливий");
        }
        double result = Math.sqrt(a);
        return new CalculationResult(result, "√" + a + " = " + format(result));
    }

    private String format(double value) {
        if (value == Math.floor(value) && !Double.isInfinite(value)) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }
}
