package tests.base;

import core.driver.DriverFactory;
import core.utils.AllureUtils;
import core.utils.TestListener;
import core.utils.property.PropertyConfig;
import org.testng.ITestResult;
import ui.manager.PageManager;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import tests.steps.ElementsNavigationSteps;

@Listeners({TestListener.class})
public class BaseTest {

    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    protected WebDriver driver;
    protected PageManager pageManager;
    protected ElementsNavigationSteps elementsNavigationSteps;

    @BeforeMethod
    public void setup(@Optional String xmlBrowser, ITestContext context) {
        String browser = resolveBrowser(xmlBrowser);
        logger.info("Starting tests on browser: {}", browser);
        driver = DriverFactory.getDriver(browser);
        driverThreadLocal.set(driver);
        pageManager = new PageManager(driver);
        elementsNavigationSteps = new ElementsNavigationSteps(pageManager);
        context.setAttribute("driver", driver);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        logger.info("Quitting driver after method");
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.error("========== TEST FAILED: {} - Taking screenshot ==========", result.getName());
            if (driver != null) {
                AllureUtils.takeScreenshot(driver);
            }
        }
        if (pageManager != null) {
            pageManager.reset();
        }
        DriverFactory.quitDriver();
        logger.info("========== DRIVER CLOSED ==========");
    }

    public WebDriver getDriver() {
        return driver;
    }

    private String resolveBrowser(String xmlBrowser) {
        if (xmlBrowser != null) {
            return xmlBrowser;
        }
        String configBrowser = PropertyConfig.getBrowser();
        return configBrowser != null ? configBrowser : "chrome";
    }
}
