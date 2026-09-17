package api.clients;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestClient {

    private static final Logger logger = LoggerFactory.getLogger(RestClient.class);
    private final String baseUrl;

    public RestClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private RequestSpecification baseRequest(String token) {
        RequestSpecification spec = given()
                .filter(new AllureRestAssured())
                .baseUri(baseUrl)
                .log().ifValidationFails();

        if (token != null && !token.isEmpty()) {
            spec.header("Authorization", "Bearer " + token);
        }
        return spec;
    }

    private void validateStatusCode(Response response, String endpoint, int expectedStatus) {
        int actualStatus = response.getStatusCode();
        if (actualStatus != expectedStatus) {
            logger.error("Request to '{}' failed. Expected: {}, Actual: {}. Body: {}",
                    endpoint, expectedStatus, actualStatus, response.getBody().asString());
            throw new RuntimeException("API Request failed with status code: " + actualStatus);
        }
    }

    // --- УНИВЕРСАЛЬНЫЕ МЕТОДЫ (GENERICS) ---

    // 1. GET без параметров
    public <T> T get(String endpoint, String token, int expectedStatus, Class<T> responseClass) {
        Response response = baseRequest(token).get(endpoint);
        validateStatusCode(response, endpoint, expectedStatus);
        return response.as(responseClass);
    }

    // 2. GET с Query-параметрами
    public <T> T get(String endpoint, Map<String, ?> queryParams, String token, int expectedStatus, Class<T> responseClass) {
        RequestSpecification spec = baseRequest(token);
        if (queryParams != null) {
            spec.queryParams(queryParams);
        }
        Response response = spec.get(endpoint);
        validateStatusCode(response, endpoint, expectedStatus);
        return response.as(responseClass);
    }

    // 3. POST с телом
    public Response post(String endpoint, Object body, String token, int expectedStatus) {
        Response response = baseRequest(token).contentType(ContentType.JSON).body(body).post(endpoint);
        validateStatusCode(response, endpoint, expectedStatus);
        return response;
    }

    // 4. POST с телом + парсинг в Объект (для логина и токена)
    public <T> T post(String endpoint, Object body, String token, int expectedStatus, Class<T> responseClass) {
        Response response = post(endpoint, body, token, expectedStatus);
        return response.as(responseClass);
    }

    // 5. DELETE с Query-параметрами
    public Response delete(String endpoint, Map<String, ?> queryParams, String token, int expectedStatus) {
        Response response = baseRequest(token).queryParams(queryParams).delete(endpoint);
        validateStatusCode(response, endpoint, expectedStatus);
        return response;
    }

    // для запросов с секретными данными (логин, пароль)
    public <T> T postWithoutAllure(String endpoint, Object body, String token, int expectedStatus, Class<T> responseClass) {
        Response response = given()
                .baseUri(baseUrl)
                .log().ifValidationFails()
                // НАМЕРЕННО НЕ добавляем .filter(new AllureRestAssured()), чтобы пароль не ушел в отчет
                .contentType(ContentType.JSON)
                .body(body)
                .post(endpoint);

        validateStatusCode(response, endpoint, expectedStatus);
        return response.as(responseClass);
    }
}
