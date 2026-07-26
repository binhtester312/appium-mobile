package utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigReader — Singleton utility to load and read config.properties.
 *
 * WHY SINGLETON: We only want to read the file once from disk.
 * All classes share the same Properties object via getInstance().
 *
 * Usage:
 *   String url = ConfigReader.getInstance().get("appium.server.url");
 */
public class ConfigReader {

    private static final Logger log = LoggerFactory.getLogger(ConfigReader.class);
    private static ConfigReader instance;
    private final Properties properties;

    // Config file path relative to classpath (src/test/resources)
    private static final String CONFIG_FILE = "configs/config.properties";

    /**
     * Private constructor — reads config file once at initialization.
     */
    private ConfigReader() {
        properties = new Properties();
        loadProperties();
    }

    /**
     * Returns the single shared instance (thread-safe double-checked locking).
     */
    public static synchronized ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    /**
     * Loads properties from classpath. Supports both classpath and file system.
     */
    private void loadProperties() {
        try (InputStream input = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {

            if (input != null) {
                properties.load(input);
                log.info("✅ Config loaded from classpath: {}", CONFIG_FILE);
            } else {
                // Fallback: try loading from file system path
                log.warn("Config not found in classpath, trying file system...");
                try (FileInputStream fis = new FileInputStream("src/test/resources/" + CONFIG_FILE)) {
                    properties.load(fis);
                    log.info("✅ Config loaded from file system.");
                }
            }
        } catch (IOException e) {
            log.error("❌ CRITICAL: Could not load config.properties — {}", e.getMessage());
            throw new RuntimeException("Failed to load configuration file: " + CONFIG_FILE, e);
        }
    }

    /**
     * Gets a property value by key.
     * Throws RuntimeException if key not found — fails fast, no silent bugs.
     *
     * @param key The property key (e.g., "appium.server.url")
     * @return The property value as String
     */
    public String get(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isEmpty()) {
            throw new RuntimeException("❌ Property '" + key + "' not found in config.properties");
        }
        return value.trim();
    }

    /**
     * Gets a property with a default value if key not found.
     *
     * @param key          The property key
     * @param defaultValue Value to return if key is missing
     * @return The property value or default
     */
    public String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue).trim();
    }

    /**
     * Gets an integer property value.
     *
     * @param key The property key
     * @return The property value as int
     */
    public int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    /**
     * Gets a boolean property value.
     *
     * @param key The property key
     * @return The property value as boolean
     */
    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}
