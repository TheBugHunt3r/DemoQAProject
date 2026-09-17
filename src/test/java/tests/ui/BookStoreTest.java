package tests.ui;

import api.models.Credentials;
import api.models.TokenResponse;
import api.services.AccountService;
import core.utils.property.PropertyConfig;
import io.qameta.allure.*;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.base.BaseTest;
import ui.pages.BookStorePage;

@Epic("Книжный магазин")
@Feature("Каталог и поиск книг")
public class BookStoreTest extends BaseTest {

    private BookStorePage bookStorePage;
    private final AccountService accountService = new AccountService();

    @BeforeMethod
    public void setUp() {
        String uiUser = PropertyConfig.getUiUsername();
        String uiPass = PropertyConfig.getUiPassword();
        logger.info("PRE-CONDITION: Быстрая авторизация через API для UI-тестов под юзером: {}", uiUser);
        Credentials credentials = new Credentials(uiUser, uiPass);
        TokenResponse authData = accountService.generateToken(credentials);
        if (authData == null || authData.getToken() == null) {
            throw new RuntimeException("Критическая ошибка pre-condition: API не вернул токен для UI-пользователя!");
        }
        driver.get("https://demoqa.com/");
        driver.manage().addCookie(new Cookie("token", authData.getToken()));
        driver.manage().addCookie(new Cookie("userName", credentials.getUserName()));
        logger.info("Куки для юзера '{}' успешно внедрены. Переходим сразу в Book Store...", uiUser);
        driver.get("https://demoqa.com/books");
        bookStorePage = pageManager.getBookStorePage();
        bookStorePage.isPageOpened();
    }

    @DataProvider(name = "bookTitles")
    public Object[][] bookTitles() {
        return new Object[][]{
                {"Programming JavaScript Applications"},
                {"Speaking JavaScript"},
                {"You Don't Know JS"}
        };
    }

    @Test(description = "Логин и переход в Book Store")
    @Story("Переход в каталог")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка успешной авторизации через API-куки и отображения главной страницы Book Store.")
    public void testLoginAndNavigateToBookStore() {
        Assert.assertNotNull(bookStorePage, "BookStorePage should not be null");
    }

    @Test(description = "Поиск книги в Book Store", dataProvider = "bookTitles")
    @Story("Поиск книг в каталоге")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка строки поиска в каталоге книг по заданным названиям.")
    public void testSearchBookInBookStore(String bookTitle) {
        bookStorePage.searchForBook(bookTitle);
        Assert.assertFalse(bookStorePage.isSearchResultsEmpty(),
                "Search results should not show 'No rows found' for: " + bookTitle);
        Assert.assertTrue(bookStorePage.isBookDisplayed(bookTitle),
                "Book '" + bookTitle + "' should be displayed in search results");
    }
}
