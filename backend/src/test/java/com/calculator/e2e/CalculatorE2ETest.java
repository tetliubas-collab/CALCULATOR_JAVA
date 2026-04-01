package com.calculator.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * E2E тести — потребують запущеного браузера (Chrome/Chromium).
 * Запуск: mvn test -Dtest=CalculatorE2ETest
 * Сервер стартує автоматично на випадковому порту під час тесту.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = "server.port=3000")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("E2E Тести — Selenium WebDriver")
class CalculatorE2ETest {

    @LocalServerPort
    private int port;

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    static void setupDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--headless",           // без відкриття вікна
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--window-size=1280,800"
        );
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) driver.quit();
    }

    @BeforeEach
    void openPage() {
        driver.get("http://localhost:" + port + "/");
        // чекаємо завантаження калькулятора
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("calculator")));
        clickById("btnClear");
    }

    // ─── E2E Тест 1: Сторінка відкривається ──────────────────────────
    @Test
    @Order(1)
    @DisplayName("E2E 1: Сторінка відкривається і містить заголовок")
    void pageLoadsCorrectly() {
        String title = driver.getTitle();
        assertTrue(title.contains("Калькулятор"), "Заголовок сторінки має містити 'Калькулятор'");

        WebElement calc = driver.findElement(By.id("calculator"));
        assertTrue(calc.isDisplayed());
    }

    // ─── E2E Тест 2: Початковий дисплей показує 0 ────────────────────
    @Test
    @Order(2)
    @DisplayName("E2E 2: При завантаженні дисплей показує 0")
    void initialDisplayIsZero() {
        String display = getDisplay();
        assertEquals("0", display);
    }

    // ─── E2E Тест 3: Введення цифр ───────────────────────────────────
    @Test
    @Order(3)
    @DisplayName("E2E 3: Натискання цифр відображається на дисплеї")
    void numberInputDisplayed() {
        clickById("btn4");
        clickById("btn2");
        assertEquals("42", getDisplay());
    }

    // ─── E2E Тест 4: Додавання через кнопки ──────────────────────────
    @Test
    @Order(4)
    @DisplayName("E2E 4: Операція додавання 8 + 7 = 15")
    void additionWorks() throws InterruptedException {
        clickById("btn8");
        clickById("btnAdd");
        clickById("btn7");
        clickById("btnEquals");

        Thread.sleep(500);
        assertEquals("15", getDisplay());
    }

    // ─── E2E Тест 5: Ділення ─────────────────────────────────────────
    @Test
    @Order(5)
    @DisplayName("E2E 5: Ділення 9 ÷ 3 = 3")
    void divisionWorks() throws InterruptedException {
        clickById("btn9");
        clickById("btnDivide");
        clickById("btn3");
        clickById("btnEquals");

        Thread.sleep(500);
        assertEquals("3", getDisplay());
    }

    // ─── E2E Тест 6: Ділення на нуль показує повідомлення ────────────
    @Test
    @Order(6)
    @DisplayName("E2E 6: Ділення на нуль — показує помилку")
    void divisionByZeroShowsError() throws InterruptedException {
        clickById("btn5");
        clickById("btnDivide");
        clickById("btn0");
        clickById("btnEquals");

        Thread.sleep(500);
        String displayText = driver.findElement(By.id("mainDisplay")).getText();
        assertFalse(displayText.isBlank(), "Дисплей має показати помилку");
    }

    // ─── E2E Тест 7: Кнопка AC очищає дисплей ────────────────────────
    @Test
    @Order(7)
    @DisplayName("E2E 7: Кнопка AC очищає введення")
    void clearButtonWorks() {
        clickById("btn9");
        clickById("btn9");
        clickById("btn9");
        assertEquals("999", getDisplay());

        clickById("btnClear");
        assertEquals("0", getDisplay());
    }

    // ─── E2E Тест 8: Кнопка Backspace видаляє символ ─────────────────
    @Test
    @Order(8)
    @DisplayName("E2E 8: Кнопка ⌫ видаляє останній символ")
    void backspaceWorks() {
        clickById("btn1");
        clickById("btn2");
        clickById("btn3");
        assertEquals("123", getDisplay());

        clickById("btnBack");
        assertEquals("12", getDisplay());
    }

    // ─── E2E Тест 9: Квадратний корінь ───────────────────────────────
    @Test
    @Order(9)
    @DisplayName("E2E 9: Квадратний корінь √16 = 4")
    void sqrtWorks() throws InterruptedException {
        clickById("btn1");
        clickById("btn6");
        clickById("btnSqrt");

        Thread.sleep(500);
        assertEquals("4", getDisplay());
    }

    // ─── Допоміжні методи ─────────────────────────────────────────────
    private void clickById(String id) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id(id))).click();
    }

    private String getDisplay() {
        return driver.findElement(By.id("mainDisplay")).getText();
    }
}
