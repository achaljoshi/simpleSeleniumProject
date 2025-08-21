package com.asx.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.*;
import org.testng.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Simple Selenium automation tests for ASX Online website
 * Tests multiple browsers using WebDriver Manager
 */
public class SimpleASXOnlineTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final String ASX_URL = "https://www.asxonline.com/";
    private static final Duration TIMEOUT = Duration.ofSeconds(10);
    private static final String BROWSER = "chrome"; // Options: "chrome", "firefox", "edge"

    /**
     * Setup method that runs once before all test methods in this class
     */
    @BeforeClass(description = "Initialize test class")
    public void setUpClass() {
        System.out.println("🏁 Starting ASX Online Test Suite");
        System.out.println("📋 Test Configuration:");
        System.out.println("   - Browser: " + BROWSER.toUpperCase());
        System.out.println("   - Target URL: " + ASX_URL);
        System.out.println("   - Timeout: " + TIMEOUT.getSeconds() + " seconds");
    }

    /**
     * Setup method that runs before each test method
     */
    @BeforeMethod(description = "Setup WebDriver before test")
    public void setUp() {
        System.out.println("🚀 Setting up WebDriver for browser: " + BROWSER.toUpperCase());
        setupDriver();
    }

    /**
     * Cleanup method that runs after each test method
     */
    @AfterMethod(description = "Cleanup WebDriver after test")
    public void tearDown() {
        System.out.println("🧹 Cleaning up WebDriver session");
        cleanupDriver();
    }

    /**
     * Cleanup method that runs once after all test methods in this class
     */
    @AfterClass(description = "Finalize test class")
    public void tearDownClass() {
        System.out.println("🏁 ASX Online Test Suite completed");
        System.out.println("📊 All tests finished successfully");
    }

    @Test(description = "Test ASX Online website navigation")
    public void testASXOnlineWebsite() {
        System.out.println("🚀 Testing ASX Online website navigation");
        testNavigateToASXOnline();
    }


    private void testNavigateToASXOnline() {
        System.out.println("Testing with browser: " + driver.getClass().getSimpleName());
        
        driver.get(ASX_URL);
        
        // Wait for page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        

        // Verify main heading is present
        WebElement mainHeading = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h1[contains(text(), 'Welcome to ASX Online')]")));
        Assert.assertTrue(mainHeading.isDisplayed(), 
            "Main heading 'Welcome to ASX Online' should be visible");
        
        driver.findElement(By.xpath("//li/a[@title='Sign-in']")).click();
        driver.findElement(By.xpath("//li/a[@title='Participant']")).click();
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);
        Assert.assertTrue(currentUrl.contains("https://auth.asxonline.com/"),
            "Current URL should contain 'https://auth.asxonline.com/'");
        
        // Wait for the email field to be visible and check if it's displayed
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("(//label[@for='idToken1'])[2]")));
        boolean isDisplayed = emailField.isDisplayed();
        Assert.assertTrue(isDisplayed, "Email field should be displayed");
    }

    /**
     * Setup WebDriver based on browser selection
     */
    private void setupDriver() {
        switch (BROWSER.toLowerCase()) {
            case "chrome":
                setupChromeDriver();
                break;
            case "firefox":
                setupFirefoxDriver();
                break;
            case "edge":
                setupEdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + BROWSER + 
                    ". Supported browsers: chrome, firefox, edge");
        }
    }

    /**
     * Setup Chrome WebDriver
     */
    private void setupChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        
        driver = new ChromeDriver(options);
        setupCommonDriverSettings();
    }

    /**
     * Setup Firefox WebDriver
     */
    private void setupFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        
        driver = new FirefoxDriver(options);
        setupCommonDriverSettings();
    }

    /**
     * Setup Edge WebDriver
     */
    private void setupEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        
        driver = new EdgeDriver(options);
        setupCommonDriverSettings();
    }

    /**
     * Setup common driver settings
     */
    private void setupCommonDriverSettings() {
        // Set up WebDriverWait for explicit waits
        wait = new WebDriverWait(driver, TIMEOUT);
        
        // Set implicit wait for WebDriver session
        driver.manage().timeouts().implicitlyWait(TIMEOUT);
        
        // Set page load timeout
        driver.manage().timeouts().pageLoadTimeout(TIMEOUT);
        
        // Set script timeout
        driver.manage().timeouts().scriptTimeout(TIMEOUT);
        
        // Maximize window
        driver.manage().window().maximize();
        
        System.out.println("🌐 Browser session configured with implicit wait: " + TIMEOUT.getSeconds() + " seconds");
    }

    /**
     * Clean up WebDriver
     */
    private void cleanupDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
