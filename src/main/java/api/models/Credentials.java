package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@ToString(exclude = "password")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Credentials {
    private String userName;
    private String password;

    public boolean isValid() {
        return userName != null && !userName.isEmpty()
                && password != null && !password.isEmpty();
    }

    public Credentials validate() {
        if (!isValid()) {
            throw new IllegalStateException(
                    "Credentials are invalid: userName or password is null/empty"
            );
        }
        return this;
    }
}
