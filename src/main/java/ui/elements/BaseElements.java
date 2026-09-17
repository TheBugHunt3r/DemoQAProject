package ui.elements;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class BaseElements {

    protected static final Logger logger = LoggerFactory.getLogger(BaseElements.class);
    protected final WebDriver driver;
    protected final By locator;
    protected int timeoutInSeconds = 10;

    // Конструкторы
    public BaseElements(WebDriver driver, By locator) {
        this(driver, locator, 10);
    }

    public BaseElements(WebDriver driver, By locator, int timeoutInSeconds) {
        this.driver = driver;
        this.locator = locator;
        this.timeoutInSeconds = timeoutInSeconds;
    }

    // Методы ожидания
    protected WebElement findElementPresence() {
        logger.debug("Waiting for presence of element: {}", locator);
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected WebElement findElementVisible() {
        logger.debug("Waiting for visibility of element: {}", locator);
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement findElementClickable() {
        logger.debug("Waiting for clickable element: {}", locator);
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Базовые действия
    public void click() {
        logger.info("Clicking on element: {}", locator);
        findElementClickable().click();
    }

    public String getText() {
        String text = findElementVisible().getText();
        logger.debug("Got text '{}' from element: {}", text, locator);
        return text;
    }

    public String getValue() {
        String value = findElementVisible().getAttribute("value");
        logger.debug("Got value '{}' from element: {}", value, locator);
        return value;
    }

    public boolean isDisplayed() {
        try {
            boolean displayed = findElementVisible().isDisplayed();
            logger.debug("Element {} is displayed: {}", locator, displayed);
            return displayed;
        } catch (NoSuchElementException | TimeoutException ex) {
            logger.debug("Element {} is not displayed: {}", locator, ex.getMessage());
            return false;
        }
    }

    public void scrollIntoView() {
        logger.debug("Scrolling to element: {}", locator);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);", findElementPresence());
    }
}
