package core.utils.retry;

import core.database.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ISuite;
import org.testng.ISuiteListener;

public class SuiteListener implements ISuiteListener {

    private static final Logger logger = LoggerFactory.getLogger(SuiteListener.class);

    @Override
    public void onStart(ISuite suite) {
        logger.info("=========================================");
        logger.info("Suite STARTED: {}", suite.getName());
        logger.info("=========================================");
    }

    @Override
    public void onFinish(ISuite suite) {
        logger.info("=========================================");
        logger.info("Suite FINISHED: {}", suite.getName());
        logger.info("=========================================");

        // Закрываем пул соединений с БД
        DatabaseManager.closePool();
    }
}
