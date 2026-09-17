package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);

    private static final String BASE_URL = "https://demoqa.com/";

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        logger.debug("BasePage initialized with driver");
    }

    protected String getBaseUrl() {
        return BASE_URL;
    }

    public void waitForPageLoaded() {
        logger.info("Waiting for page to load (document.readyState = complete)");
        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState")
                .toString()
                .equals("complete"));
        logger.info("Page loaded successfully");
    }

    protected void waitForElementVisible(By locator) {
        logger.debug("Waiting for element to become visible: {}", locator);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        logger.debug("Element is visible");
    }

    protected void waitForElementClickable(WebElement element) {
        logger.debug("Waiting for element to become clickable: {}", element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        logger.debug("Element is clickable");
    }

    protected void waitForElementPresent(By locator) {
        logger.debug("Waiting for element to be present: {}", locator);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        logger.debug("Element is present");
    }

    protected void scrollToElement(WebElement element) {
        logger.debug("Scrolling to element: {}", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        logger.debug("Scrolling completed");
    }
}
