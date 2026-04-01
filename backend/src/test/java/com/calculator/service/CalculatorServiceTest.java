package com.calculator.service;

import com.calculator.model.CalculationRequest;
import com.calculator.model.CalculationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CalculatorService — Юніт Тести")
class CalculatorServiceTest {

    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorService();
    }

    // ─── Тест 1: Додавання ───────────────────────────────────────────
    @Test
    @DisplayName("Тест 1: Додавання двох цілих чисел")
    void testAddition() {
        CalculationResult result = calculatorService.add(10, 5);

        assertFalse(result.isError());
        assertEquals(15.0, result.getResult(), 0.0001);
        assertTrue(result.getExpression().contains("15"));
    }

    // ─── Тест 2: Від'ємання ──────────────────────────────────────────
    @Test
    @DisplayName("Тест 2: Від'ємання — результат від'ємний")
    void testSubtraction() {
        CalculationResult result = calculatorService.subtract(3, 10);

        assertFalse(result.isError());
        assertEquals(-7.0, result.getResult(), 0.0001);
    }

    // ─── Тест 3: Множення ────────────────────────────────────────────
    @Test
    @DisplayName("Тест 3: Множення на нуль завжди дає нуль")
    void testMultiplicationByZero() {
        CalculationResult result = calculatorService.multiply(9999, 0);

        assertFalse(result.isError());
        assertEquals(0.0, result.getResult(), 0.0001);
    }

    // ─── Тест 4: Ділення ─────────────────────────────────────────────
    @Test
    @DisplayName("Тест 4: Коректне ділення дробових чисел")
    void testDivision() {
        CalculationResult result = calculatorService.divide(7, 2);

        assertFalse(result.isError());
        assertEquals(3.5, result.getResult(), 0.0001);
    }

    // ─── Тест 5: Ділення на нуль ─────────────────────────────────────
    @Test
    @DisplayName("Тест 5: Ділення на нуль повертає помилку")
    void testDivisionByZero() {
        CalculationResult result = calculatorService.divide(42, 0);

        assertTrue(result.isError());
        assertNotNull(result.getErrorMessage());
        assertTrue(result.getErrorMessage().toLowerCase().contains("нуль"));
    }

    // ─── Тест 6: Степінь ─────────────────────────────────────────────
    @Test
    @DisplayName("Тест 6: Зведення у степінь")
    void testPower() {
        CalculationResult result = calculatorService.power(2, 10);

        assertFalse(result.isError());
        assertEquals(1024.0, result.getResult(), 0.0001);
    }

    // ─── Тест 7: Квадратний корінь ───────────────────────────────────
    @Test
    @DisplayName("Тест 7: Квадратний корінь з правильного числа")
    void testSqrt() {
        CalculationResult result = calculatorService.sqrt(144);

        assertFalse(result.isError());
        assertEquals(12.0, result.getResult(), 0.0001);
    }

    // ─── Тест 8: Корінь з від'ємного числа ───────────────────────────
    @Test
    @DisplayName("Тест 8: Корінь з від'ємного числа — помилка")
    void testSqrtNegative() {
        CalculationResult result = calculatorService.sqrt(-25);

        assertTrue(result.isError());
        assertNotNull(result.getErrorMessage());
    }

    // ─── Тест 9: Залишок від ділення (modulo) ─────────────────────────
    @Test
    @DisplayName("Тест 9: Залишок від ділення")
    void testModulo() {
        CalculationResult result = calculatorService.modulo(17, 5);

        assertFalse(result.isError());
        assertEquals(2.0, result.getResult(), 0.0001);
    }

    // ─── Тест 10: Невідома операція через calculate() ─────────────────
    @Test
    @DisplayName("Тест 10: Невідома операція через calculate() повертає помилку")
    void testUnknownOperation() {
        CalculationRequest req = new CalculationRequest(5, 3, "unknownOp");
        CalculationResult result = calculatorService.calculate(req);

        assertTrue(result.isError());
    }
}
