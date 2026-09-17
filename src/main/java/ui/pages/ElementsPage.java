package ui.pages;

import io.qameta.allure.Step;
import ui.dto.FormData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.wrappers.Buttons;
import ui.wrappers.Input;
import ui.wrappers.RadioButton;

import static ui.elements.Elements.ElementsPage.*;

public class ElementsPage extends BasePage {

    public ElementsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("Проверка открытия страницы 'Elements'")
    public ElementsPage isPageOpened() {
        logger.info("Checking Elements page opened");
        waitForPageLoaded();
        waitForElementVisible(ELEMENTS_TITLE);
        return this;
    }

    // === Методы для Text Box ===
    @Step("Переход в раздел 'Text Box'")
    public ElementsPage openTextBoxSection() {
        logger.info("Opening Text Box section");
        scrollToElement(driver.findElement(TEXT_BOX_BUTTON));
        new Buttons(driver, TEXT_BOX_BUTTON).click();
        return this;
    }

    @Step("Заполнение формы 'Text Box'")
    public ElementsPage fillTextBoxForm(FormData form) {
        logger.info("Filling Text Box form");
        new Input(driver, "Full Name").write(form.getFirstName() + " " + form.getLastName());
        new Input(driver, "Email").write(form.getEmail());
        new Input(driver, "Current Address").write(form.getCurrentAddress());
        new Input(driver, "Permanent Address").write(form.getPermanentAddress());
        return this;
    }

    @Step("Отправка формы 'Text Box' (Submit)")
    public ElementsPage submitTextBoxForm() {
        logger.info("Submitting Text Box form");
        scrollToElement(driver.findElement(SUBMIT_BUTTON));
        new Buttons(driver, SUBMIT_BUTTON).click();
        return this;
    }

    public boolean isTextBoxFormSubmitted() {
        return isElementPresent(SUCCESS_MESSAGE);
    }

    public String getTextBoxSuccessMessage() {
        return driver.findElement(SUCCESS_MESSAGE).getText();
    }

    // === Методы для Radio Button ===
    @Step("Переход в раздел 'Radio Button'")
    public ElementsPage openRadioButtonSection() {
        logger.info("Opening Radio Button section");
        scrollToElement(driver.findElement(RADIO_BUTTON_SECTION));
        new Buttons(driver, RADIO_BUTTON_SECTION).click();
        return this;
    }

    @Step("Выбор радио-баттона: '{value}'")
    public ElementsPage selectRadioButton(String value) {
        logger.info("Selecting radio button: {}", value);
        new RadioButton(driver, value).select();
        return this;
    }

    public String getSelectedRadioButtonText() {
        String text = driver.findElement(RADIO_BUTTON_SUCCESS_MESSAGE).getText();
        logger.debug("Selected radio button text: {}", text);
        return text;
    }

    // === Методы для Web Tables ===
    @Step("Переход в раздел 'Web Tables'")
    public ElementsPage openWebTablesSection() {
        logger.info("Opening Web Tables section");
        scrollToElement(driver.findElement(WEB_TABLES_BUTTON));
        new Buttons(driver, WEB_TABLES_BUTTON).click();
        waitForElementVisible(By.id("addNewRecordButton"));
        return this;
    }

    @Step("Нажатие кнопки 'Add New Record'")
    public ElementsPage clickAddNewRecord() {
        logger.info("Clicking Add New Record button");
        new Buttons(driver, By.id("addNewRecordButton")).click();
        return this;
    }

    @Step("Заполнение формы добавления записи в Web Table")
    public ElementsPage fillWebTableForm(FormData form) {
        logger.info("Filling Web Table form");
        new Input(driver, "FirstName").write(form.getFirstName());
        new Input(driver, "LastName").write(form.getLastName());
        new Input(driver, "Email").write(form.getEmail());
        new Input(driver, "Age").write(form.getAge());
        new Input(driver, "Salary").write(form.getSalary());
        new Input(driver, "Department").write(form.getDepartment());
        return this;
    }

    @Step("Сохранение записи в Web Table")
    public ElementsPage submitWebTableForm() {
        logger.info("Submitting Web Table form");
        new Buttons(driver, By.id("submit")).click();
        return this;
    }

    public boolean isRecordPresent(String firstName) {
        String xpath = "//div[text()='" + firstName + "']";
        return isElementPresent(By.xpath(xpath));
    }

    // === Методы для Buttons ===
    @Step("Переход в раздел 'Buttons'")
    public ElementsPage openButtonsSection() {
        logger.info("Opening Buttons section");
        scrollToElement(driver.findElement(BUTTONS));
        new Buttons(driver, BUTTONS).click();
        return this;
    }

    @Step("Клик правой кнопкой мыши по кнопке '{buttonText}'")
    public ElementsPage rightClickOnButton(String buttonText) {
        logger.info("Right clicking on button: {}", buttonText);
        new Buttons(driver, buttonText).rightClick();
        return this;
    }

    @Step("Двойной клик по кнопке '{buttonText}'")
    public ElementsPage doubleClickOnButton(String buttonText) {
        logger.info("Double clicking on button: {}", buttonText);
        new Buttons(driver, buttonText).doubleClick();
        return this;
    }

    @Step("Клик по кнопке '{buttonText}'")
    public ElementsPage clickOnButton(String buttonText) {
        logger.info("Clicking on button: {}", buttonText);
        new Buttons(driver, buttonText).click();
        return this;
    }

    public String getRightClickMessage() {
        return driver.findElement(By.id("rightClickMessage")).getText();
    }

    public String getDoubleClickMessage() {
        return driver.findElement(By.id("doubleClickMessage")).getText();
    }

    public String getDynamicClickMessage() {
        return driver.findElement(By.id("dynamicClickMessage")).getText();
    }

    // === Методы для Links ===
    @Step("Переход в раздел 'Links'")
    public ElementsPage openLinksSection() {
        logger.info("Opening Links section");
        scrollToElement(driver.findElement(LINKS_BUTTON));
        new Buttons(driver, LINKS_BUTTON).click();
        waitForElementVisible(LINKS);
        return this;
    }

    @Step("Клик по ссылке '{linkText}'")
    public ElementsPage clickLink(String linkText) {
        logger.info("Clicking on link: {}", linkText);
        waitForElementClickable(By.linkText(linkText));
        driver.findElement(By.linkText(linkText)).click();
        return this;
    }

    public boolean isUrlContains(String expectedUrlPart) {
        boolean contains = driver.getCurrentUrl().contains(expectedUrlPart);
        logger.debug("URL contains '{}': {}", expectedUrlPart, contains);
        return contains;
    }

    // === Методы для Response (API Links) ===
    @Step("Переход в раздел 'API Links/Response'")
    public ElementsPage openResponseSection() {
        logger.info("Opening Response section");
        scrollToElement(driver.findElement(LINKS));
        new Buttons(driver, LINKS).click();
        waitForElementVisible(By.linkText("Created"));
        return this;
    }

    @Step("Клик по API-ссылке '{linkText}'")
    public ElementsPage clickResponseLink(String linkText) {
        logger.info("Clicking on response link: {}", linkText);
        waitForElementClickable(By.linkText(linkText));
        driver.findElement(By.linkText(linkText)).click();
        return this;
    }

    public String getLinkResponseText() {
        waitForElementVisible(By.id("linkResponse"));
        return driver.findElement(By.id("linkResponse")).getText();
    }

    public boolean isLinkResponseContains(String expectedText) {
        String response = getLinkResponseText();
        logger.debug("Link response contains '{}': {}", expectedText, response.contains(expectedText));
        return response.contains(expectedText);
    }

    // === Вспомогательные методы ===
    private boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void waitForElementClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}
