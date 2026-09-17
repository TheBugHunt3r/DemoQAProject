package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {

    private String token;
    private String expires;
    private String status;
    private String result;
    private String userId;
    private String username;

    public boolean isSuccess() {
        return "Success".equalsIgnoreCase(status);
    }

    public boolean hasToken() {
        return token != null && !token.isEmpty();
    }

    public String getTokenOrThrow() {
        if (!hasToken()) {
            throw new IllegalStateException(
                    "Token is missing in response. Status: " + status + ", Result: " + result
            );
        }
        return token;
    }
}
