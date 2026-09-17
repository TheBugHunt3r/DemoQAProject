package core.driver;

import org.openqa.selenium.WebDriver;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();

    private DriverFactory() {
        // приватный конструктор
    }

    public static WebDriver getDriver(String browser) {
        WebDriver driver = driverPool.get();
        if (driver == null) {
            driver = createDriver(browser);
            driverPool.set(driver);
        }
        return driver;
    }

    private static WebDriver createDriver(String browser) {
        return DriverOptions.createDriver(browser);
    }

    public static void quitDriver() {
        WebDriver driver = driverPool.get();
        if (driver != null) {
            driver.quit();
            driverPool.remove();
        }
    }
}
