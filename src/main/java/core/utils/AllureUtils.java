package core.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;

public class AllureUtils {

    private static final Logger logger = LoggerFactory.getLogger(AllureUtils.class);

    @Attachment(value = "Screenshot on failure", type = "image/png")
    public static byte[] takeScreenshot(WebDriver driver) {
        if (driver == null) {
            logger.warn("Cannot take screenshot: WebDriver is null");
            return null;
        }
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage());
            return null;
        }
    }
}
