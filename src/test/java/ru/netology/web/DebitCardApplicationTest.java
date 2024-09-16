package ru.netology.web;

import org.junit.jupiter.api.AfterEach; //
import org.junit.jupiter.api.BeforeAll; //
import org.junit.jupiter.api.BeforeEach; //
import org.junit.jupiter.api.Test; //
import org.openqa.selenium.By; //
import org.openqa.selenium.WebDriver; //
import org.openqa.selenium.WebElement; //
import org.openqa.selenium.chrome.ChromeDriver; //
import org.openqa.selenium.chrome.ChromeOptions; //
import io.github.bonigarcia.wdm.WebDriverManager; //

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebitCardApplicationTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        // Настройка ChromeOptions для headless-режима
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless"); // Включение headless-режима
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void orderFormSubmissionTest() {
        // Проверяем заполнение полей
        WebElement nameInput = driver.findElement(By.cssSelector("[data-test-id=name] input"));
        WebElement phoneInput = driver.findElement(By.cssSelector("[data-test-id=phone] input"));
        WebElement consentCheckbox = driver.findElement(By.cssSelector("[data-test-id=agreement]"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type=button]"));

        // Ввод данных в поля
        nameInput.sendKeys("Цыбулька Alina"); // Фамилия и Имя на русском
        phoneInput.sendKeys("+79858930397"); //Валидный номер телефона
        consentCheckbox.click(); // Установка флажка согласия

        // Отправка формы
        submitButton.click();

        // Проверка результата
        WebElement successMessage = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", successMessage.getText().trim());
    }
}


