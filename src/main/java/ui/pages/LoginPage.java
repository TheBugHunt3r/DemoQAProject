package ui.pages;

import api.models.Credentials;
import core.database.DatabaseManager;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.wrappers.Buttons;
import ui.wrappers.Input;

import java.time.Duration;
import java.util.Map;

import static ui.elements.Elements.Main.LOGOUT_BUTTON;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("Открытие страницы логина")
    public LoginPage open() {
        logger.info("Opening Login page");
        driver.get(getBaseUrl() + "login");
        return this;
    }

    @Step("Авторизация с учетными данными: {credentials}")
    public LoginPage login(Credentials credentials) {
        logger.info("Logging in with credentials for user: {}", credentials.getUserName());
        new Input(driver, "userName").write(credentials.getUserName());
        new Input(driver, "password").write(credentials.getPassword());
        new Buttons(driver, "Login").click();
        return this;
    }

    @Step("Авторизация под пользователем, полученным из базы данных")
    public LoginPage loginFromDatabase() {
        logger.info("Logging in from database");
        Map<String, String> creds = DatabaseManager.getUserCredentials("admin6");
        if (creds == null || creds.get("username") == null || creds.get("password") == null) {
            throw new IllegalStateException("Failed to load user credentials from database for user 'admin'! Check init.sql execution.");
        }
        Credentials credentials = new Credentials(creds.get("username"), creds.get("password"));
        return login(credentials);
    }

    @Step("Проверка успешности входа в систему (отображение кнопки 'Log out')")
    public boolean isLoginSuccessful() {
        logger.info("Verifying if login was successful by waiting for 'Log out' text...");
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(LOGOUT_BUTTON));
            logger.info("Login verified: 'Log out' button is visible.");
            return true;
        } catch (Exception e) {
            logger.warn("Login failed: 'Log out' button did not appear.");
            return false;
        }
    }

    @Step("Нажатие кнопки 'Go To Book Store' и переход в каталог")
    public BookStorePage moveToBookStore() {
        logger.info("Moving to Book Store page");
        new Buttons(driver, "Go To Book Store").jsClick();
        return new BookStorePage(driver);
    }
}
