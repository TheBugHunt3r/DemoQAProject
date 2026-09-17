package tests.unit;

import api.clients.RestClient;
import api.models.BookListResponse;
import api.services.BookStoreService;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Map;
import static org.mockito.Mockito.*;

@Epic("Unit Tests")
@Feature("BookStore Service Unit Tests")
public class BookStoreServiceUnitTest {

    @Mock
    private RestClient restClientMock;

    @Mock
    private Response responseMock;

    private BookStoreService bookStoreService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bookStoreService = new BookStoreService(restClientMock);
    }

    @Test(description = "Проверка получения списка книг с помощью мока")
    @Story("Мокание метода получения каталога книг /BookStore/v1/Books")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка корректного обращения сервиса к RestClient за полным списком книг и возвращения его без ошибок.")
    public void testGetBooksSuccess() {
        BookListResponse mockResponse = new BookListResponse();
        when(restClientMock.get(eq("/BookStore/v1/Books"), isNull(), eq(200), eq(BookListResponse.class)))
                .thenReturn(mockResponse);

        BookListResponse actualResponse = bookStoreService.getBooks();

        Assert.assertNotNull(actualResponse);
        verify(restClientMock, times(1))
                .get(eq("/BookStore/v1/Books"), isNull(), eq(200), eq(BookListResponse.class));
    }

    @Test(description = "Проверка обработки ошибки при поиске книги по ISBN")
    @Story("Обработка ошибок при запросе книги по ISBN")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверяет, что при возникновении ошибки на стороне RestClient метод getBookByIsbn " +
            "перехватывает исключение и возвращает null.")
    public void testGetBookByIsbnNotFound() {
        when(restClientMock.get(eq("/BookStore/v1/Book"), anyMap(), isNull(), eq(200), eq(BookListResponse.class)))
                .thenThrow(new RuntimeException("API Error 404"));

        BookListResponse response = bookStoreService.getBookByIsbn("invalid-isbn");

        Assert.assertNull(response, "При ошибке метод должен возвращать null");
        verify(restClientMock, times(1)).get(eq("/BookStore/v1/Book"), any(), isNull(),
                eq(200), eq(BookListResponse.class));
    }

    @Test(description = "Проверка удаления всех книг пользователя")
    @Story("Мокание метода очистки коллекции пользователя /BookStore/v1/Books")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяет вызов метода DELETE для очистки списка книг конкретного пользователя с передачей токена.")
    public void testDeleteAllBooks() {
        when(restClientMock.delete(eq("/BookStore/v1/Books"), eq(Map.of("UserId", "123")),
                eq("mock-token"), eq(204)))
                .thenReturn(responseMock);

        Response actualResponse = bookStoreService.deleteAllBooks("123", "mock-token");

        Assert.assertEquals(actualResponse, responseMock);
        verify(restClientMock, times(1)).delete(anyString(), anyMap(), anyString(), eq(204));
    }
}
