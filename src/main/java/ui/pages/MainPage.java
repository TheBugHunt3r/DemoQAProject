package ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ui.elements.Elements.Main.ELEMENTS_BUTTON;
import static ui.elements.Elements.Main.TITLE;

public class MainPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(MainPage.class);

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("Открытие главной страницы demoqa.com")
    public MainPage open() {
        logger.info("Opening main page: {}", getBaseUrl());
        driver.get(getBaseUrl());
        return this;
    }

    @Step("Проверка открытия главной страницы")
    public MainPage isPageOpened() {
        logger.debug("Checking that main page is opened");
        waitForPageLoaded();
        waitForElementPresent(TITLE);
        return this;
    }

    @Step("Клик по карточке 'Elements' и переход на страницу Elements")
    public ElementsPage moveToElements() {
        logger.info("Navigating to Elements page");
        WebElement el = driver.findElement(ELEMENTS_BUTTON);
        scrollToElement(el);
        el.click();
        logger.info("Successfully navigated to Elements page");
        return new ElementsPage(driver);
    }
}
