package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookListResponse {

    private List<Book> books;

    public List<Book> getBooksSafe() {
        return books != null ? books : new ArrayList<>();
    }

    public boolean isEmpty() {
        return books == null || books.isEmpty();
    }

    public int getBookCount() {
        return books != null ? books.size() : 0;
    }
}
