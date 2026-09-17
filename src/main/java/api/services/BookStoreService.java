package api.services;

import api.clients.RestClient;
import api.models.AddBookRequest;
import api.models.BookListResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class BookStoreService {

    private static final Logger logger = LoggerFactory.getLogger(BookStoreService.class);
    private final RestClient client;

    public BookStoreService() {
        this.client = new RestClient("https://demoqa.com");
    }

    public BookStoreService(RestClient client) {
        this.client = client;
    }

    @Step("API: Запрос списка всех книг (GET /BookStore/v1/Books)")
    public BookListResponse getBooks() {
        logger.info("Getting all books");
        BookListResponse response = client.get("/BookStore/v1/Books", null, 200, BookListResponse.class);
        logger.info("Retrieved {} books", response.getBookCount());
        return response;
    }

    @Step("API: Добавление книги ISBN '{isbn}' пользователю '{userId}'")
    public Response addBook(String userId, String isbn, String token) {
        logger.info("Adding book with ISBN '{}' to user '{}'", isbn, userId);
        AddBookRequest request = AddBookRequest.withBook(userId, isbn);
        Response response = client.post("/BookStore/v1/Books", request, token, 201);
        logger.info("Book added successfully");
        return response;
    }

    @Step("API: Удаление всех книг пользователя '{userId}'")
    public Response deleteAllBooks(String userId, String token) {
        logger.info("Deleting all books for user '{}'", userId);
        Response response = client.delete("/BookStore/v1/Books", Map.of("UserId", userId), token, 204);
        logger.info("All books deleted successfully");
        return response;
    }

    @Step("API: Запрос книги по ISBN '{isbn}' (GET /BookStore/v1/Book)")
    public BookListResponse getBookByIsbn(String isbn) {
        logger.info("Getting book by ISBN: {}", isbn);
        try {
            return client.get("/BookStore/v1/Book", Map.of("ISBN", isbn), null, 200, BookListResponse.class);
        } catch (RuntimeException e) {
            logger.warn("Book with ISBN '{}' not found or request failed", isbn);
            return null;
        }
    }

    @Step("API: Получение списка книг пользователя '{userId}'")
    public BookListResponse getUserBooks(String userId, String token) {
        logger.info("Getting books for user '{}'", userId);
        BookListResponse response = client.get("/Account/v1/User/" + userId, token, 200, BookListResponse.class);
        return response;
    }
}
