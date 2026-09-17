package ui.wrappers;

import ui.elements.BaseElements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Buttons extends BaseElements {

    private static final Logger logger = LoggerFactory.getLogger(Buttons.class);

    // Конструктор по тексту кнопки
    public Buttons(WebDriver driver, String text) {
        super(driver, By.xpath("//button[normalize-space(text())='" + text + "']"));
    }

    // Конструктор по локатору
    public Buttons(WebDriver driver, By locator) {
        super(driver, locator);
    }

    // Конструктор с кастомным таймаутом
    public Buttons(WebDriver driver, String text, int timeoutInSeconds) {
        super(driver, By.xpath("//button[normalize-space(text())='" + text + "']"), timeoutInSeconds);
    }

    public Buttons(WebDriver driver, By locator, int timeoutInSeconds) {
        super(driver, locator, timeoutInSeconds);
    }

    @Override
    public void click() {
        logger.info("Clicking on button: {}", getLocatorDescription());
        findElementClickable().click();
    }

    public Buttons jsClick() {
        logger.info("JavaScript click on button: {}", getLocatorDescription());
        findElementClickable();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", findElementPresence());
        return this;
    }

    public Buttons actionClick() {
        logger.info("Action click on button: {}", getLocatorDescription());
        findElementClickable();
        new Actions(driver).moveToElement(findElementVisible()).click().perform();
        return this;
    }

    public Buttons doubleClick() {
        logger.info("Double click on button: {}", getLocatorDescription());
        findElementClickable();
        new Actions(driver).doubleClick(findElementClickable()).perform();
        return this;
    }

    public Buttons ctrlClick() {
        logger.info("Ctrl+click on button: {}", getLocatorDescription());
        findElementClickable();
        new Actions(driver)
                .keyDown(Keys.CONTROL)
                .click(findElementClickable())
                .keyUp(Keys.CONTROL)
                .perform();
        return this;
    }

    public Buttons rightClick() {
        logger.info("Right click on button: {}", getLocatorDescription());
        findElementClickable();
        new Actions(driver).contextClick(findElementClickable()).perform();
        return this;
    }

    public Buttons hover() {
        logger.info("Hovering over button: {}", getLocatorDescription());
        findElementVisible();
        new Actions(driver).moveToElement(findElementVisible()).perform();
        return this;
    }

    private String getLocatorDescription() {
        return locator.toString();
    }
}
