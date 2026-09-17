package ui.wrappers;

import ui.elements.BaseElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class PickList extends BaseElements {

    private static final Logger logger = LoggerFactory.getLogger(PickList.class);
    private final String label;

    // Конструктор
    public PickList(WebDriver driver, String label) {
        super(driver, By.xpath("//label[normalize-space(text())='" + label + "']//following::div[contains(@class,'placeholder')]"));
        this.label = label;
    }

    // Конструктор с кастомным таймаутом
    public PickList(WebDriver driver, String label, int timeoutInSeconds) {
        super(driver, By.xpath("//label[normalize-space(text())='" + label + "']//following::div[contains(@class,'placeholder')]"), timeoutInSeconds);
        this.label = label;
    }

    @Override
    public void click() {
        logger.debug("Clicking on picklist: {}", label);
        findElementClickable().click();
    }

    public PickList select(String option) {
        logger.info("Selecting option '{}' from picklist '{}'", option, label);

        // Открываем дропдаун
        click();

        // Ждём и кликаем по опции
        WebElement optionElement = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[contains(@class,'menu')]//div[normalize-space(text())='" + option + "']")
                ));
        optionElement.click();

        // Проверяем, что опция выбралась
        String selectedText = getSelectedOption();
        if (!selectedText.equals(option)) {
            throw new AssertionError("Option '" + option + "' was not selected. Selected: '" + selectedText + "'");
        }

        logger.info("Option '{}' successfully selected", option);
        return this;
    }

    public String getSelectedOption() {
        String selected = findElementVisible().getText();
        logger.debug("Selected option in picklist '{}': '{}'", label, selected);
        return selected;
    }

    public boolean isOptionSelected(String expectedOption) {
        return getSelectedOption().equals(expectedOption);
    }
}
