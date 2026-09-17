package ui.manager;

import org.openqa.selenium.WebDriver;
import ui.pages.BookStorePage;
import ui.pages.ElementsPage;
import ui.pages.LoginPage;
import ui.pages.MainPage;

import java.util.HashMap;
import java.util.Map;

public class PageManager {
    private final WebDriver driver;
    private final Map<Class<?>, Object> pages = new HashMap<>();

    public PageManager(WebDriver driver) {
        this.driver = driver;
    }

    @SuppressWarnings("unchecked")
    public <T> T getPage(Class<T> pageClass) {
        return (T) pages.computeIfAbsent(pageClass, k -> {
            try {
                return pageClass.getConstructor(WebDriver.class).newInstance(driver);
            } catch (Exception e) {
                throw new RuntimeException("Could not create page " + pageClass, e);
            }
        });
    }

    public MainPage getMainPage() {
        return getPage(MainPage.class);
    }

    public ElementsPage getElementsPage() {
        return getPage(ElementsPage.class);
    }

    public LoginPage getLoginPage() {
        return getPage(LoginPage.class);
    }

    public BookStorePage getBookStorePage() {
        return getPage(BookStorePage.class);
    }

    public void reset() {
        pages.clear();
    }
}
