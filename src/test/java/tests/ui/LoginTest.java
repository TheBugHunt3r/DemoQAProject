package tests.ui;

import api.models.Credentials;
import core.utils.property.PropertyConfig;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.base.BaseTest;
import ui.pages.LoginPage;

@Epic("Авторизация и безопасность")
@Feature("Форма логина")
public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        loginPage = pageManager.getLoginPage().open();
    }

    @DataProvider(name = "invalidPasswords")
    public Object[][] invalidPasswords() {
        return new Object[][]{
                {"WrongPassword!"},
                {""},
                {"123"},
                {"a@b#c$d%"},
                {PropertyConfig.getApiPassword() + "extra"}
        };
    }

    @Test(description = "Авторизация пользователя с корректными данными")
    @Story("Успешный вход")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Проверка успешной авторизации с валидными логином и паролем из конфигурации.")
    public void testOfLoginWithValidCredentials() {
        Credentials creds = new Credentials(PropertyConfig.getApiUsername(), PropertyConfig.getApiPassword());
        loginPage.login(creds);
        Assert.assertTrue(loginPage.isLoginSuccessful(),
                "User should be logged in successfully");
    }

    @Test(description = "Авторизация пользователя с неверными данными",
            dataProvider = "invalidPasswords")
    @Story("Отказ в доступе при использовании невалидных данных")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка отклонения входа при использовании списка невалидных паролей.")
    public void testOfLoginWithInvalidCredentials(String wrongPassword) {
        Credentials creds = new Credentials(PropertyConfig.getApiUsername(), wrongPassword);
        loginPage.login(creds);
        Assert.assertFalse(loginPage.isLoginSuccessful(),
                "User should NOT be logged in with password: " + wrongPassword);
    }

    @Test(description = "Авторизация пользователя из базы данных")
    @Story("Авторизация через БД")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка входа в систему с использованием учетных данных, полученных из базы данных.")
    public void loginFromDatabase() {
        loginPage.loginFromDatabase();
        Assert.assertTrue(loginPage.isLoginSuccessful(),
                "User from database should be logged in successfully");
    }
}
