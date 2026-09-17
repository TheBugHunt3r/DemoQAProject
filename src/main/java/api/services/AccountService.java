package api.services;

import api.clients.RestClient;
import api.models.Credentials;
import api.models.TokenResponse;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private final RestClient client;

    public AccountService() {
        this.client = new RestClient("https://demoqa.com");
    }

    public AccountService(RestClient client) {
        this.client = client;
    }

    @Step("API: Запрос авторизации для пользователя '{0.userName}'")
    public TokenResponse login(Credentials authData) {
        logger.info("Sending login request for user: {}", authData.getUserName());
        TokenResponse response = client.postWithoutAllure("/Account/v1/Login", authData, null, 200, TokenResponse.class);
        logger.info("Login successful");
        return response;
    }

    @Step("API: Запрос генерации токена для пользователя '{0.userName}'")
    public TokenResponse generateToken(Credentials authData) {
        logger.info("Sending generate token request for user: {}", authData.getUserName());
        TokenResponse tokenResponse = client.postWithoutAllure("/Account/v1/GenerateToken", authData, null, 200, TokenResponse.class);
        if (!tokenResponse.isSuccess()) {
            logger.warn("Token generation returned status: {}, result: {}", tokenResponse.getStatus(), tokenResponse.getResult());
        } else {
            logger.info("Token generated successfully");
        }
        return tokenResponse;
    }
}
