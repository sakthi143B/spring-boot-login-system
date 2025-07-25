package com.example.demo;

import java.time.Duration;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginRegisterSeleniumTest {

    static WebDriver driver;
    static WebDriverWait wait;

    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Test Register Page")
    public void testRegisterPage() throws InterruptedException {
        driver.get("http://localhost:8080/register"); // Update path if needed

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));
        driver.findElement(By.name("name")).sendKeys("Test User");
        driver.findElement(By.name("email")).sendKeys("sonas23bca151@skasc.ac.in");
        driver.findElement(By.name("password")).sendKeys("tharun");

        WebElement form = driver.findElement(By.tagName("form"));
        form.submit();

        Thread.sleep(2000); // Allow time for redirection

        // Assuming registration redirects to login
        Assertions.assertTrue(driver.getCurrentUrl().contains("/login"));
    }

    @Test
    @DisplayName("Test Login Page")
    public void testLoginPage() throws InterruptedException {
        driver.get("http://localhost:8080/login");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        driver.findElement(By.name("email")).sendKeys("sonas23bca151@skasc.ac.in");
        driver.findElement(By.name("password")).sendKeys("tharun");

        WebElement form = driver.findElement(By.tagName("form"));
        form.submit();

        Thread.sleep(2000); // Allow time for redirection

        Assertions.assertTrue(driver.getCurrentUrl().contains("/user/dashboard") ||
                              driver.getCurrentUrl().contains("/admin/dashboard"));
    }

    @AfterAll
    public static void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
