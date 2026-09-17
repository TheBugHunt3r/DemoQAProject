package tests.base;

import api.models.Credentials;
import api.models.TokenResponse;
import api.services.AccountService;
import api.services.BookStoreService;
import core.utils.TestListener;
import core.utils.property.PropertyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public class BaseApiTest {

    protected AccountService accountService = new AccountService();
    protected BookStoreService bookStoreService = new BookStoreService();
    protected Logger logger = LoggerFactory.getLogger(BaseApiTest.class);

    protected String token;
    protected String userId;

    @BeforeClass
    public void setUpApi(ITestContext context) {
        logger.info("Setting up API credentials...");
        refreshToken();
        context.setAttribute("token", token);
    }

    protected void refreshToken() {
        String user = PropertyConfig.getApiUsername();
        String pass = PropertyConfig.getApiPassword();
        Credentials credentials = new Credentials(user, pass);
        TokenResponse loginResponse = accountService.login(credentials);
        userId = loginResponse.getUserId();
        TokenResponse tokenResponse = accountService.generateToken(credentials);
        token = tokenResponse.getToken();
        if (token == null || userId == null) {
            logger.error("API Login failed for user: {}", user);
            throw new RuntimeException("API Setup failed! Check config.properties or server availability.");
        }
        logger.info("API Setup successful. UserID: {}", userId);
    }
}
