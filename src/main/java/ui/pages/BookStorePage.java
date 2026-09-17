package ui.pages;

import io.qameta.allure.Step;
import ui.elements.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import ui.wrappers.Buttons;
import ui.wrappers.Input;

import java.util.List;

import static ui.elements.Elements.BookStore.SEARCH_INPUT;

public class BookStorePage extends BasePage {

    public BookStorePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("Проверка открытия страницы 'Book Store'")
    public BookStorePage isPageOpened() {
        logger.info("Checking if Book Store page is opened");
        waitForPageLoaded();
        waitForElementPresent(SEARCH_INPUT);
        return this;
    }

    @Step("Ввод названия книги в строку поиска: '{bookTitle}'")
    public BookStorePage searchForBook(String bookTitle) {
        logger.info("Searching for book: {}", bookTitle);
        new Input(driver, SEARCH_INPUT).write(bookTitle);
        return this;
    }

    @Step("Проверка отображения книги: '{bookTitle}'")
    public boolean isBookDisplayed(String bookTitle) {
        logger.info("Checking if book is displayed: {}", bookTitle);
        try {
            org.openqa.selenium.By bookLocator = org.openqa.selenium.By.linkText(bookTitle.trim());
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(bookLocator));
            logger.info("✅ Книга '{}' визуально отображается на экране!", bookTitle);
            return true;
        } catch (Exception e) {
            logger.warn("❌ Книга '{}' не найдена через By.linkText", bookTitle);
            return false;
        }
    }

    public int getBooksCount() {
        logger.debug("Getting books count in table");
        List<WebElement> rows = driver.findElements(By.xpath("//div[@class='rt-tr-group']"));
        return rows.size();
    }

    public boolean isSearchResultsEmpty() {
        try {
            WebElement noDataNode = driver.findElement(By.xpath("//div[@class='rt-noData']"));
            return noDataNode.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Клик по книге в каталоге: '{bookTitle}'")
    public BookStorePage clickOnBook(String bookTitle) {
        logger.info("Clicking on book: {}", bookTitle);
        By bookLink = Elements.BookStore.getSearchResultLocator(bookTitle);
        driver.findElement(bookLink).click();
        return this;
    }

    @Step("Возврат обратно в каталог книг (Back to Book Store)")
    public BookStorePage goBackToBookStore() {
        logger.info("Going back to Book Store");
        new Buttons(driver, "Back to Book Store").click();
        return this;
    }
}
