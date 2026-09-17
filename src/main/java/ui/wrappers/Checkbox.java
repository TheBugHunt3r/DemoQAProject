package ui.wrappers;

import ui.elements.BaseElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Checkbox extends BaseElements {

    private static final Logger logger = LoggerFactory.getLogger(Checkbox.class);
    private final String label;

    // Конструктор с дефолтным таймаутом
    public Checkbox(WebDriver driver, String label) {
        this(driver, label, 10);
    }

    // Конструктор с кастомным таймаутом
    public Checkbox(WebDriver driver, String label, int timeoutInSeconds) {
        super(driver, By.xpath("//li[.//span[contains(@class,'rct-title') and normalize-space()='" + label + "']]//span[contains(@class,'rct-checkbox')]"), timeoutInSeconds);
        this.label = label;
    }

    @Override
    public void click() {
        logger.info("Clicking on checkbox: {}", label);
        findElementClickable().click();
    }

    public Checkbox check() {
        if (!isChecked()) {
            logger.info("Checkbox '{}' is not checked, checking now", label);
            click();
        } else {
            logger.debug("Checkbox '{}' is already checked, skipping", label);
        }
        return this;
    }

    public Checkbox uncheck() {
        if (isChecked()) {
            logger.info("Checkbox '{}' is checked, unchecking now", label);
            click();
        } else {
            logger.debug("Checkbox '{}' is already unchecked, skipping", label);
        }
        return this;
    }

    public boolean isChecked() {
        boolean checked = findElementPresence().isSelected();
        logger.debug("Checkbox '{}' checked status: {}", label, checked);
        return checked;
    }
}
