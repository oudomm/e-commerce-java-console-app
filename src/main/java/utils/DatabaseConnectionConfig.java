package utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionConfig {
    private static final Properties properties = new Properties();

    // Load properties once when class is loaded
    static {
        try (InputStream input = DatabaseConnectionConfig.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                System.err.println("[!] Unable to find application.properties file");
                throw new RuntimeException("application.properties file not found");
            }

            properties.load(input);
//            System.out.println("[✓] Database configuration loaded successfully");

        } catch (IOException e) {
            System.err.println("[!] Error loading database configuration: " + e.getMessage());
            throw new RuntimeException("Failed to load database configuration", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            String databaseUrl = properties.getProperty("db.url");
            String userName = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            // Get timeout settings from properties
            int connectionTimeout = Integer.parseInt(properties.getProperty("db.connection.timeout", "10"));
            int socketTimeout = Integer.parseInt(properties.getProperty("db.socket.timeout", "30"));
            boolean tcpKeepAlive = Boolean.parseBoolean(properties.getProperty("db.tcp.keepalive", "true"));

            // Build connection URL with parameters
            String urlWithTimeout = databaseUrl +
                    "?connectTimeout=" + connectionTimeout +
                    "&socketTimeout=" + socketTimeout +
                    "&tcpKeepAlive=" + tcpKeepAlive;

            Connection conn = DriverManager.getConnection(urlWithTimeout, userName, password);
//            System.out.println("[✓] Connected to database: " + databaseUrl);
            return conn;

        } catch (SQLException e) {
            System.err.println("[!] ERROR during database connection: " + e.getMessage());
            System.err.println("[!] Check if PostgreSQL is running: brew services start postgresql");
            throw e;
        } catch (NumberFormatException e) {
            System.err.println("[!] Invalid timeout configuration in properties file");
            throw new SQLException("Invalid database configuration", e);
        }
    }

    // Alternative method that returns null (for backward compatibility)
    public static Connection getConnectionSafe() {
        try {
            return getConnection();
        } catch (SQLException e) {
            return null;
        }
    }

    // Method to test database connectivity
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("[!] Database connection test failed: " + e.getMessage());
            return false;
        }
    }

    // Utility method to get any property value
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    // Utility method to get property with default value
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}