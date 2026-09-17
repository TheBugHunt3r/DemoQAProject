package tests.unit;

import api.clients.RestClient;
import api.models.Credentials;
import api.models.TokenResponse;
import api.services.AccountService;
import io.qameta.allure.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

@Epic("Unit Tests")
@Feature("Account Service Unit Tests")
public class AccountServiceUnitTest {

    @Mock
    private RestClient restClientMock;

    private AccountService accountService;

    private Credentials credentials;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        accountService = new AccountService(restClientMock);
        credentials = new Credentials("testUser", "Password123!");
    }

    @Test(description = "Успешный логин пользователя через мок")
    @Story("Мокание вызова авторизации /Account/v1/Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка корректного вызова сервисом HTTP POST метода у RestClient для эндпоинта логина и " +
            "возвращения ожидаемого ответа.")
    public void testLoginSuccess() {
        TokenResponse mockResponse = new TokenResponse();
        when(restClientMock.postWithoutAllure(eq("/Account/v1/Login"), eq(credentials), isNull(), eq(200), eq(TokenResponse.class)))
                .thenReturn(mockResponse);
        TokenResponse actualResponse = accountService.login(credentials);
        Assert.assertNotNull(actualResponse);
        verify(restClientMock, times(1))
                .postWithoutAllure(eq("/Account/v1/Login"), eq(credentials), isNull(), eq(200), eq(TokenResponse.class));
    }

    @Test(description = "Успешная генерация токена (isSuccess = true)")
    @Story("Мокание вызова генерации токена /Account/v1/GenerateToken")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяет успешный сценарий генерации токена с возвратом флага isSuccess = true.")
    public void testGenerateTokenSuccess() {
        TokenResponse mockResponse = mock(TokenResponse.class);
        when(mockResponse.isSuccess()).thenReturn(true);
        when(restClientMock.postWithoutAllure(eq("/Account/v1/GenerateToken"), eq(credentials), isNull(), eq(200), eq(TokenResponse.class)))
                .thenReturn(mockResponse);
        TokenResponse actualResponse = accountService.generateToken(credentials);
        Assert.assertTrue(actualResponse.isSuccess());
        verify(restClientMock, times(1))
                .postWithoutAllure(eq("/Account/v1/GenerateToken"), eq(credentials), isNull(), eq(200), eq(TokenResponse.class));
    }

    @Test(description = "Неуспешная генерация токена (isSuccess = false)")
    @Story("Мокание ошибки генерации токена /Account/v1/GenerateToken")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверяет негативный сценарий генерации токена и логирование статуса/причины ошибки.")
    public void testGenerateTokenFailure() {
        TokenResponse mockResponse = mock(TokenResponse.class);
        when(mockResponse.isSuccess()).thenReturn(false);
        when(mockResponse.getStatus()).thenReturn("Failed");
        when(mockResponse.getResult()).thenReturn("User authorization failed.");
        when(restClientMock.postWithoutAllure(eq("/Account/v1/GenerateToken"), eq(credentials), isNull(), eq(200), eq(TokenResponse.class)))
                .thenReturn(mockResponse);
        TokenResponse actualResponse = accountService.generateToken(credentials);
        Assert.assertFalse(actualResponse.isSuccess());
        verify(restClientMock, times(1))
                .postWithoutAllure(eq("/Account/v1/GenerateToken"), eq(credentials), isNull(), eq(200), eq(TokenResponse.class));
        verify(mockResponse, times(1)).getStatus();
        verify(mockResponse, times(1)).getResult();
    }
}
