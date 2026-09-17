package ui.wrappers;

import ui.elements.BaseElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Input extends BaseElements {

    private static final Logger logger = LoggerFactory.getLogger(Input.class);

    // Конструкторы
    public Input(WebDriver driver, By locator) {
        super(driver, locator, 15);
    }

    public Input(WebDriver driver, By locator, int timeoutSeconds) {
        super(driver, locator, timeoutSeconds);
    }

    public Input(WebDriver driver, String label) {
        this(driver, buildLocatorFromLabel(label));
    }

    public Input write(String text) {
        logger.info("Writing '{}' to input: {}", text, locator);
        findElementVisible().clear();
        findElementClickable().sendKeys(text);
        return this;
    }

    public Input clear() {
        logger.debug("Clearing input: {}", locator);
        findElementVisible().clear();
        return this;
    }

    @Override
    public void click() {
        logger.info("Clicking on input: {}", locator);
        findElementClickable().click();
    }

    // Получение значения
    public String getValue() {
        String value = findElementVisible().getAttribute("value");
        logger.debug("Got value '{}' from input: {}", value, locator);
        return value;
    }

    // Проверка видимости
    public boolean isVisible() {
        try {
            findElementVisible();
            logger.debug("Input is visible: {}", locator);
            return true;
        } catch (Exception e) {
            logger.debug("Input is not visible: {}", locator);
            return false;
        }
    }

    // === статические методы для построения локаторов ===

    private static By buildLocatorFromLabel(String label) {
        String esc = escapeForXPath(label);
        String xpath = "//label[contains(normalize-space(.)," + esc + ")]//following::input[1]"
                + " | //input[contains(normalize-space(@placeholder)," + esc + ") or @id=" + esc + " or @name=" + esc + "]";
        return By.xpath(xpath);
    }

    private static String escapeForXPath(String input) {
        if (!input.contains("'")) {
            return "'" + input + "'";
        }
        String[] parts = input.split("'");
        StringBuilder sb = new StringBuilder("concat(");
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) {
                sb.append(", \"'\", ");
            }
            sb.append("'").append(parts[i]).append("'");
        }
        sb.append(")");
        return sb.toString();
    }
}
