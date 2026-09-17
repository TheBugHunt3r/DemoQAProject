package ui.wrappers;

import ui.elements.BaseElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextArea extends BaseElements {

    private static final Logger logger = LoggerFactory.getLogger(TextArea.class);
    private final String label;

    public TextArea(WebDriver driver, String label) {
        super(driver, By.xpath("//label[normalize-space(text())='" + label + "']//following::textarea[1]"));
        this.label = label;
    }

    public TextArea write(String value) {
        logger.info("Writing to textarea '{}': {}", label, value);
        findElementVisible().clear();
        findElementClickable().sendKeys(value);
        return this;
    }

    public String getValue() {
        String value = findElementVisible().getAttribute("value");
        logger.debug("Textarea '{}' value: {}", label, value);
        return value;
    }

    public TextArea clear() {
        logger.debug("Clearing textarea: {}", label);
        findElementVisible().clear();
        return this;
    }
}
