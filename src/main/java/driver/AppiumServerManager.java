package driver;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.ConfigReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class AppiumServerManager {

    private static final Logger log = LoggerFactory.getLogger(AppiumServerManager.class);
    private static AppiumDriverLocalService service;

    private AppiumServerManager() {
    }

    /**
     * Starts the Appium Server programmatically if auto-start is enabled in config.
     */
    public static synchronized void startServer() {
        ConfigReader config = ConfigReader.getInstance();

        // Check system properties first, fallback to config.properties (default: false)
        boolean autoStart = Boolean.parseBoolean(
                System.getProperty("run_local_server",
                        System.getProperty("appium.auto.start.server",
                                config.get("appium.auto.start.server", "false"))));

        if (!autoStart) {
            log.info("ℹ️ Appium Local Server Auto-Start is DISABLED (run_local_server=false). " +
                    "Connecting to static URL: [{}]", config.get("appium.server.url"));
            return;
        }

        if (service != null && service.isRunning()) {
            log.info("ℹ️ Appium Local Server is already running at: {}", service.getUrl());
            return;
        }

        log.info("🚀 Starting Appium Local Server programmatically...");

        try {
            AppiumServiceBuilder builder = new AppiumServiceBuilder()
                    .withIPAddress("127.0.0.1")
                    .usingAnyFreePort() // Dynamically uses any free port to avoid "Port in use" conflicts
                    .withTimeout(Duration.ofSeconds(30));

            service = AppiumDriverLocalService.buildService(builder);
            service.start();

            log.info("✅ Appium Local Server started successfully at: {}", service.getUrl());
        } catch (Exception e) {
            log.error("❌ Failed to start Appium Local Server: {}", e.getMessage(), e);
            throw new RuntimeException("Appium Local Server failed to start.", e);
        }
    }

    /**
     * Returns the active Appium Server URL.
     * Uses dynamic service URL if auto-started, otherwise falls back to static
     * config URL.
     *
     * @return URL instance for Appium Server connection
     */
    public static URL getServerUrl() {
        if (service != null && service.isRunning()) {
            return service.getUrl();
        }

        ConfigReader config = ConfigReader.getInstance();
        String staticUrlStr = config.get("appium.server.url", "http://127.0.0.1:4723");
        try {
            return new URL(staticUrlStr);
        } catch (MalformedURLException e) {
            log.error("❌ Malformed Appium server URL in config: {}", staticUrlStr);
            throw new RuntimeException("Invalid Appium server URL: " + staticUrlStr, e);
        }
    }

    /**
     * Stops the local Appium Server if it was started programmatically.
     */
    public static synchronized void stopServer() {
        if (service != null && service.isRunning()) {
            log.info("🛑 Stopping Appium Local Server...");
            try {
                service.stop();
                log.info("✅ Appium Local Server stopped successfully.");
            } catch (Exception e) {
                log.warn("⚠️ Error stopping Appium Local Server: {}", e.getMessage());
            } finally {
                service = null;
            }
        }
    }

    /**
     * Checks if the local Appium Server is currently running.
     *
     * @return true if service is running
     */
    public static boolean isServerRunning() {
        return service != null && service.isRunning();
    }
}
