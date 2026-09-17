package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddBookRequest {

    private String userId;
    private List<IsbnEntry> collectionOfIsbns;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class IsbnEntry {
        private String isbn;
    }

    public static AddBookRequest withBook(String userId, String isbn) {
        return AddBookRequest.builder()
                .userId(userId)
                .collectionOfIsbns(List.of(new IsbnEntry(isbn)))
                .build();
    }
}
