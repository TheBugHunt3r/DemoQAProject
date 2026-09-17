package core.utils.property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyConfig {

    private static final Logger logger = LoggerFactory.getLogger(PropertyConfig.class);

    private static final Properties props = new Properties();

    static {
        try (InputStream input = PropertyConfig.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found in classpath");
            }
            props.load(input);
            logger.info("✅ config.properties loaded successfully");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    private static String getDecodedProperty(String key) {
        //переменная окружения (для CI/CD)
        String envKey = key.replace(".", "_").toUpperCase();
        String value = System.getenv(envKey);
        //из файла config.properties
        if (value == null) {
            value = props.getProperty(key);
        }
        // Расшифровка, если значение начинается с "enc_"
        if (value != null && value.startsWith("enc_")) {
            return CryptoUtils.decode(value.substring(4));
        }
        return value;
    }

    // === Методы для работы с конфигурацией ===

    public static String getBrowser() {
        return props.getProperty("grid.browser", "chrome");
    }

    public static String getBaseUrl() {
        String url = props.getProperty("base.url");
        if (url == null) {
            throw new IllegalStateException("base.url is not configured in config.properties");
        }
        return url;
    }

    public static String getApiUsername() {
        return getDecodedProperty("api.username");
    }

    public static String getApiPassword() {
        return getDecodedProperty("api.password");
    }

    // === НОВЫЕ МЕТОДЫ ДЛЯ UI ЮЗЕРА ===
    public static String getUiUsername() {
        return props.getProperty("ui.username");
    }

    public static String getUiPassword() {
        return getDecodedProperty("ui.password");
    }

    public static String getDbPassword() {
        return getDecodedProperty("db.password");
    }

    public static String getGridUrl() {
        return props.getProperty("grid.url");
    }

    // === Настройки для DriverOptions ===

    public static boolean isHeadless() {
        return "true".equalsIgnoreCase(props.getProperty("headless", "false"));
    }

    public static boolean isMaximized() {
        return "true".equalsIgnoreCase(props.getProperty("maximized", "true"));
    }

    public static int getWindowWidth() {
        return Integer.parseInt(props.getProperty("window.width", "1920"));
    }

    public static int getWindowHeight() {
        return Integer.parseInt(props.getProperty("window.height", "1080"));
    }

    public static int getTimeout() {
        return Integer.parseInt(props.getProperty("timeout", "20"));
    }

    // === Настройки для базы данных ===

    public static String getDbUrl() {
        String url = props.getProperty("db.url");
        if (url == null) {
            throw new IllegalStateException("db.url is not configured in config.properties");
        }
        return url;
    }

    public static String getDbUser() {
        String user = props.getProperty("db.user");
        if (user == null) {
            throw new IllegalStateException("db.user is not configured in config.properties");
        }
        return user;
    }
}
