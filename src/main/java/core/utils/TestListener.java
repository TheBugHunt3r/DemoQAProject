package core.utils;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("========== STARTING TEST: {} ==========", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("========== PASSED TEST: {} ==========", result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("========== SKIPPED TEST: {} ==========", result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("========== SUITE STARTED: {} ==========", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("========== SUITE FINISHED: {} ==========", context.getName());
    }
}