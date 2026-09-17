package tests.api;

import api.models.BookListResponse;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.base.BaseApiTest;

public class BookStoreTest extends BaseApiTest {

    private static final Logger logger = LoggerFactory.getLogger(BookStoreTest.class);
    private String firstBookIsbn;

    @BeforeMethod
    public void setUp() {
        logger.info("Cleaning up user's book collection before test");
        bookStoreService.deleteAllBooks(userId, token);

        BookListResponse books = bookStoreService.getBooks();
        if (!books.isEmpty()) {
            firstBookIsbn = books.getBooks().get(0).getIsbn();
            logger.info("First book ISBN: {}", firstBookIsbn);
        }
    }

    @Test(description = "Получение списка всех книг")
    public void testGetBooks() {
        logger.info("Starting test: Get all books");

        BookListResponse books = bookStoreService.getBooks();

        Assert.assertNotNull(books);
        Assert.assertFalse(books.isEmpty(), "Books list should not be empty");

        logger.info("Retrieved {} books", books.getBookCount());
    }

    @Test(description = "Добавление книги в коллекцию")
    public void testAddBook() {
        logger.info("Starting test: Add book to collection. ISBN: {}", firstBookIsbn);

        Response response = bookStoreService.addBook(userId, firstBookIsbn, token);

        Assert.assertEquals(response.getStatusCode(), 201, "Add book should return 201 Created");

        logger.info("Book added successfully, verifying...");

        // Дополнительная проверка — можно получить книгу по ISBN
        BookListResponse userBooks = bookStoreService.getUserBooks(userId, token);
        boolean found = userBooks.getBooks().stream()
                .anyMatch(book -> book.getIsbn().equals(firstBookIsbn));

        Assert.assertTrue(found, "Book with ISBN " + firstBookIsbn + " should be in user's collection");
        logger.info("Book verified in user's collection");
    }

    @Test(description = "Удаление всех книг из коллекции")
    public void testDeleteBooks() {
        logger.info("Starting test: Delete all books from collection");

        // Добавляем книгу
        bookStoreService.addBook(userId, firstBookIsbn, token);
        logger.info("Added test book before deletion");

        // Удаляем все книги
        Response response = bookStoreService.deleteAllBooks(userId, token);
        Assert.assertEquals(response.getStatusCode(), 204, "Delete all should return 204 No Content");

        // Проверяем, что коллекция пуста
        BookListResponse userBooks = bookStoreService.getUserBooks(userId, token);
        Assert.assertTrue(userBooks.isEmpty(), "User's book collection should be empty");

        logger.info("All books deleted successfully");
    }
}
