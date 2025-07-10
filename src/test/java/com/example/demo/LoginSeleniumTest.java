package com.example.demo;
import java.time.Duration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginSeleniumTest {

    static WebDriver driver;

    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().setup();  // Automatically sets up ChromeDriver
        driver = new ChromeDriver();              // Launches Chrome browser
    }

    @Test
    public void testLoginPage() {
        driver.get("http://localhost:8080/login");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
wait.until(ExpectedConditions.titleContains("Login")); // your login page URL

        driver.findElement(By.name("email")).sendKeys("admin@example.com"); // use valid test data
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.tagName("form")).submit();

        Assertions.assertTrue(driver.getCurrentUrl().contains("/admin/dashboard"));
    }

    @AfterAll
    public static void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
