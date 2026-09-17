package core.utils.property;

import java.util.Base64;

public class CryptoUtils {

    static String encode(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    static String decode(String encodedPassword) {
        return new String(Base64.getDecoder().decode(encodedPassword));
    }

    //это удалить после запуска
    public static void main(String[] args) {
        String encoded = encode("admin");
        System.out.println("Encoded password: " + encoded);
        String decoded = decode(encoded);
        System.out.println("Decoded: " + decoded);
    }
}
