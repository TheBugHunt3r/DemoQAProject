package ui.wrappers;

import ui.elements.BaseElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RadioButton extends BaseElements {

    private static final Logger logger = LoggerFactory.getLogger(RadioButton.class);
    private final String value;

    public RadioButton(WebDriver driver, String value) {
        super(driver, By.xpath("//label[text()='" + value + "']"));
        this.value = value;
    }

    public RadioButton select() {
        if (!isSelected()) {
            logger.info("Selecting radio button: {}", value);
            click();
            logger.info("Radio button '{}' selected", value);
        } else {
            logger.debug("Radio button '{}' is already selected, skipping", value);
        }
        return this;
    }

    public boolean isSelected() {
        boolean selected = findElementPresence().isSelected();
        logger.debug("Radio button '{}' selected status: {}", value, selected);
        return selected;
    }
}
