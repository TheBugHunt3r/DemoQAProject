package core.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import core.utils.property.PropertyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DatabaseManager {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);

    private static final HikariDataSource dataSource;

    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(PropertyConfig.getDbUrl());
            config.setUsername(PropertyConfig.getDbUser());
            config.setPassword(PropertyConfig.getDbPassword());
            config.setMaximumPoolSize(10);           // максимум 10 соединений в пуле
            config.setMinimumIdle(2);                // минимум 2 свободных соединения
            config.setConnectionTimeout(30000);       // таймаут получения соединения: 30 секунд
            config.setIdleTimeout(600000);            // таймаут простоя: 10 минут
            config.setMaxLifetime(1800000);           // максимальное время жизни соединения: 30 минут

            dataSource = new HikariDataSource(config);
            logger.info("✅ Database connection pool initialized");
        } catch (Exception e) {
            logger.error("Failed to initialize database connection pool", e);
            throw new RuntimeException("Database pool initialization failed", e);
        }
    }

    // Получение соединения из пула
    private static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // Закрытие пула (вызывать при завершении всех тестов)
    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            logger.info("Database connection pool closed");
        }
    }

    // Универсальный метод для выполнения запросов
    private static <T> T executeQuery(String query, String param, Function<ResultSet, T> mapper) {
        logger.debug("Executing query: {}", query);

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, param);
            ResultSet rs = pstmt.executeQuery();

            return mapper.apply(rs);

        } catch (SQLException e) {
            logger.error("Query failed: {}", query, e);
            throw new RuntimeException("Database query failed", e);
        }
    }

    public static Map<String, String> getUserCredentials(String userRole) {
        logger.info("Fetching credentials for user role: {}", userRole);

        String query = "SELECT username, password FROM users WHERE role = ?";

        return executeQuery(query, userRole, rs -> {
            Map<String, String> credentials = new HashMap<>();
            try {
                if (rs.next()) {
                    String username = rs.getString("username");
                    logger.info("Found user: {}", username);
                    credentials.put("username", username);
                    credentials.put("password", rs.getString("password"));
                } else {
                    logger.warn("No user found with role: {}", userRole);
                }
            } catch (SQLException e) {
                logger.error("Error processing result set", e);
            }
            return credentials;
        });
    }
}
