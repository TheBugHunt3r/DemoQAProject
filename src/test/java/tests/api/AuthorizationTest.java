package tests.api;

import api.models.Credentials;
import api.models.TokenResponse;
import core.utils.property.PropertyConfig;
import io.qameta.allure.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.base.BaseApiTest;

@Epic("API Account Service")
@Feature("Авторизация и управление токенами")
public class AuthorizationTest extends BaseApiTest {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationTest.class);

    private Credentials getCredentials() {
        String username = PropertyConfig.getApiUsername();
        String password = PropertyConfig.getApiPassword();
        logger.debug("Creating credentials for user: {}", username);
        return new Credentials(username, password);
    }

    @Test(description = "Авторизация пользователя")
    @Story("Логин пользователя через API")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Проверка успешного логина пользователя с получением имени пользователя и токена доступа.")
    public void testLogin() {
        logger.info("Starting test: User login");
        Credentials credentials = getCredentials();
        logger.info("Attempting login for user: {}", credentials.getUserName());
        TokenResponse loginResponse = accountService.login(credentials);
        Assert.assertEquals(loginResponse.getUsername(), credentials.getUserName(),
                "Username in response should match requested user");
        Assert.assertNotNull(loginResponse.getToken(), "Token should not be null");
        logger.info("Login successful for user: {}", credentials.getUserName());
    }

    @Test(description = "Генерация токена доступа")
    @Story("Генерация JWT/Bearer токена")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка выполнения эндпоинта GenerateToken и возврата статуса Success с активным токеном.")
    public void testGenerateToken() {
        logger.info("Starting test: Generate access token");
        Credentials credentials = getCredentials();
        logger.info("Requesting token for user: {}", credentials.getUserName());
        TokenResponse tokenResponse = accountService.generateToken(credentials);
        Assert.assertNotNull(tokenResponse.getToken(), "Token should not be null");
        Assert.assertTrue(tokenResponse.isSuccess(), "Token generation status should be Success");
        logger.info("Token generated successfully. Token: {}",
                maskToken(tokenResponse.getToken()));
    }

    // Вспомогательный метод
    private String maskToken(String token) {
        if (token == null || token.length() <= 8) {
            return "***";
        }
        return token.substring(0, 6) + "..." + token.substring(token.length() - 4);
    }
}
