package ui.elements;

import org.openqa.selenium.By;

public class Elements {

    public static final class Login {
        public static final By USERNAME_INPUT = By.id("userName");
        public static final By PASSWORD_INPUT = By.id("password");
        public static final By LOGIN_BUTTON = By.id("login");
        public static final By PROFILE_LINK = By.xpath("//span[contains(text(), 'Profile')]");
        public static final By INVALID_LOGIN_MESSAGE = By.xpath("//p[contains(text(), 'Invalid username or password!')]");

        private Login() {}
    }

    // === Book Store Page ===
    public static final class BookStore {
        public static final By NEW_USER_BUTTON = By.xpath("//button[contains(text(), 'New User')]");
        public static final By REGISTER_BUTTON = By.id("register");
        public static final By SEARCH_INPUT = By.id("searchBox");

        // Новый лаконичный локатор для сбора всех книг в таблице
        public static final By ALL_BOOK_LINKS = By.xpath("//div[@class='rt-tbody']//a");

        public static By getSearchResultLocator(String text) {
            return By.xpath(String.format("//a[contains(text(), '%s')]", text));
        }

        private BookStore() {}
    }

    // === Elements Page ===
    public static final class ElementsPage {
        public static final By ELEMENTS_TITLE = By.xpath("//div[@class='header-wrapper']//div[contains(text(), 'Elements')]");
        public static final By TEXT_BOX_BUTTON = By.xpath("//span[contains(text(),'Text Box')]");
        public static final By LINKS_BUTTON = By.xpath("//span[contains(text(),'Links')]");
        public static final By SUBMIT_BUTTON = By.xpath("//button[@id='submit']");
        public static final By SUCCESS_MESSAGE = By.xpath("//div[@class='border col-md-12 col-sm-12']");
        public static final By CHECKBOX_BUTTON = By.xpath("//span[contains(text(), 'Check Box')]");
        public static final By RADIO_BUTTON_SECTION = By.xpath("//span[contains(text(),'Radio Button')]");
        public static final By WEB_TABLES_BUTTON = By.xpath("//span[contains(text(), 'Web Tables')]");
        public static final By BUTTONS = By.xpath("//span[contains(text(),'Buttons')]");
        public static final By LINKS = By.xpath("//span[contains(text(),'Links')]");
        public static final By RADIO_BUTTON_SUCCESS_MESSAGE = By.xpath("//span[@class='text-success']");

        private ElementsPage() {}
    }

    // === Forms Page ===
    public static final class Forms {
        public static final By FORM_PAGE_BUTTON = By.xpath("//div[@class='header-wrapper']//div[contains(text(), 'Forms')]");
        public static final By PRACTICE_FORM = By.xpath("//span[contains(text(),'Practice Form')]");
        public static final By FORM_TITLE = By.xpath("//h5[contains(text(), 'Student Registration Form')]");
        public static final By SUBMIT_FORM_BUTTON = By.xpath("//button[@type='submit']");
        public static final By SUCCESS_FORM_MESSAGE = By.xpath("//div[contains(text(), 'Thanks for submitting the form')]");

        private Forms() {}
    }

    // === Main Page ===
    public static final class Main {
        public static final By TITLE = By.xpath("//div[@class='card-body']//h5[contains(text(), 'Elements')]");
        public static final By ELEMENTS_BUTTON = By.xpath("//div[@class='card-body']//h5[contains(text(), 'Elements')]");
        public static final By LOGOUT_BUTTON = By.xpath("//button[text()='Logout']");

        private Main() {}
    }

    private Elements() {}
}
