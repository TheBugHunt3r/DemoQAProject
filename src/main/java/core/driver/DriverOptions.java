package core.driver;

import core.utils.property.PropertyConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverOptions {
    private DriverOptions() {
        // приватный конструктор
    }

    public static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");

        if (PropertyConfig.isHeadless()) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=" + PropertyConfig.getWindowWidth() + "," + PropertyConfig.getWindowHeight());
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        } else if (PropertyConfig.isMaximized()) {
            options.addArguments("--start-maximized");
        } else {
            options.addArguments("--window-size=" + PropertyConfig.getWindowWidth() + "," + PropertyConfig.getWindowHeight());
        }

        return options;
    }

    public static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();

        if (PropertyConfig.isHeadless()) {
            options.addArguments("--headless");
            options.addArguments("--width=" + PropertyConfig.getWindowWidth());
            options.addArguments("--height=" + PropertyConfig.getWindowHeight());
        } else if (PropertyConfig.isMaximized()) {
            options.addArguments("--start-maximized");
        }

        return options;
    }


    //Создает драйвер (локально или в Grid):

    public static WebDriver createDriver(String browser) {
        String gridUrl = PropertyConfig.getGridUrl();

        if (gridUrl != null && !gridUrl.isEmpty()) {
            // Режим Grid — удалённый запуск
            return createRemoteDriver(browser, gridUrl);
        } else {
            // Локальный режим
            return createLocalDriver(browser);
        }
    }

    private static WebDriver createLocalDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                return new ChromeDriver(getChromeOptions());
            case "firefox":
                return new FirefoxDriver(getFirefoxOptions());
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    private static WebDriver createRemoteDriver(String browser, String gridUrl) {
        try {
            URL url = new URL(gridUrl);
            switch (browser.toLowerCase()) {
                case "chrome":
                    return new RemoteWebDriver(url, getChromeOptions());
                case "firefox":
                    return new RemoteWebDriver(url, getFirefoxOptions());
                default:
                    throw new IllegalArgumentException("Unsupported browser for grid: " + browser);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Grid URL: " + gridUrl, e);
        }
    }
}
