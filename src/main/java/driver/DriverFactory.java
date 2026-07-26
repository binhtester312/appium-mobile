package driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.ConfigReader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * DriverFactory — Creates and manages AndroidDriver instances.
 *
 * DESIGN PATTERN: ThreadLocal Pattern
 * WHY: Each test thread gets its own AndroidDriver, enabling true
 * parallel test execution without driver conflicts.
 *
 * HOW IT WORKS:
 *   - DriverFactory.initDriver()  → creates driver for current thread
 *   - DriverFactory.getDriver()   → retrieves driver for current thread
 *   - DriverFactory.quitDriver()  → quits and removes driver for current thread
 */
public class DriverFactory {

    private static final Logger log = LoggerFactory.getLogger(DriverFactory.class);

    /**
     * ThreadLocal ensures each test thread has its own isolated AndroidDriver.
     * This is the key to safe parallel test execution.
     */
    private static final ThreadLocal<AndroidDriver> driverThreadLocal = new ThreadLocal<>();

    // Prevent instantiation — this is a static utility class
    private DriverFactory() {}

    /**
     * Initializes a new AndroidDriver for the current thread.
     * Reads all configuration from config.properties via ConfigReader.
     *
     * Called once per test class in @BeforeClass or @BeforeSuite.
     */
    public static void initDriver() {
        if (driverThreadLocal.get() != null) {
            log.warn("Driver already exists for this thread. Quitting existing driver first.");
            quitDriver();
        }

        ConfigReader config = ConfigReader.getInstance();

        // --- Build Appium capabilities using UiAutomator2Options ---
        // UiAutomator2Options is the type-safe, strongly-typed way to set
        // capabilities in Appium 2/3 (replaces deprecated DesiredCapabilities)
        UiAutomator2Options options = new UiAutomator2Options();

        // Device identification
        options.setDeviceName(config.get("device.name"));
        options.setUdid(config.get("udid"));
        options.setPlatformVersion(config.get("platform.version"));

        // Automation engine — must match the installed Appium driver
        options.setAutomationName(config.get("automation.name"));

        // App configuration — use appPackage + appActivity for installed apps
        // OR use setApp() for .apk file path
        String appPath = config.get("app.path", "");
        if (!appPath.isEmpty()) {
            File appFile = new File(appPath);
            options.setApp(appFile.getAbsolutePath());
            log.info("Using app file (absolute path): {}", appFile.getAbsolutePath());
        } else {
            options.setAppPackage(config.get("app.package"));
            options.setAppActivity(config.get("app.activity"));
            log.info("Using installed app: {}/{}", config.get("app.package"), config.get("app.activity"));
        }

        // Set appWaitActivity to avoid timeout issues when SplashActivity transitions quickly to MainActivity.
        // Using '*' allows Appium to wait for any activity of the package to start.
        String appWaitActivity = config.get("app.wait.activity", "*");
        options.setAppWaitActivity(appWaitActivity);
        log.info("AppWaitActivity set to: {}", appWaitActivity);

        // Don't reset the app state between test sessions
        // Set to true if you want a fresh app state each run
        options.setNoReset(true);

        // Auto-grant permissions — avoids popups interrupting tests
        options.setAutoGrantPermissions(true);

        // Timeouts
        options.setNewCommandTimeout(Duration.ofSeconds(config.getInt("implicit.wait") * 6L));

        try {
            URL appiumServerUrl = new URL(config.get("appium.server.url"));
            log.info("🚀 Starting AndroidDriver...");
            log.info("   Server: {}", appiumServerUrl);
            log.info("   Device: {} ({})", config.get("device.name"), config.get("udid"));

            AndroidDriver driver = new AndroidDriver(appiumServerUrl, options);

            // Set implicit wait — Appium waits up to N seconds for elements
            driver.manage().timeouts().implicitlyWait(
                    Duration.ofSeconds(config.getInt("implicit.wait"))
            );

            driverThreadLocal.set(driver);
            log.info("✅ AndroidDriver initialized successfully.");

        } catch (MalformedURLException e) {
            log.error("❌ Invalid Appium server URL: {}", config.get("appium.server.url"));
            throw new RuntimeException("Invalid Appium server URL", e);
        } catch (Exception e) {
            log.error("❌ Failed to initialize AndroidDriver: {}", e.getMessage());
            throw new RuntimeException("AndroidDriver initialization failed. " +
                    "Is Appium server running? Is the emulator started?", e);
        }
    }

    /**
     * Returns the AndroidDriver for the current thread as AppiumDriver.
     * Using AppiumDriver type avoids compile-time access to removed Selenium 3
     * interfaces (ContextAware, LocationContext) in AndroidDriver's class hierarchy.
     *
     * @return AppiumDriver instance for this thread
     */
    public static AppiumDriver getDriver() {
        AndroidDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new IllegalStateException(
                    "❌ Driver is null! Call DriverFactory.initDriver() in @BeforeClass first."
            );
        }
        return driver;
    }

    /**
     * Quits the AndroidDriver and removes it from ThreadLocal.
     * IMPORTANT: Always call this in @AfterClass to prevent resource leaks.
     */
    public static void quitDriver() {
        AndroidDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
                log.info("✅ AndroidDriver quit successfully.");
            } catch (Exception e) {
                log.warn("⚠️ Error while quitting driver: {}", e.getMessage());
            } finally {
                // Always remove from ThreadLocal to prevent memory leaks
                driverThreadLocal.remove();
            }
        } else {
            log.warn("⚠️ quitDriver() called but no driver exists for this thread.");
        }
    }

    /**
     * Checks if driver is currently active for this thread.
     *
     * @return true if driver is initialized and active
     */
    public static boolean isDriverActive() {
        return driverThreadLocal.get() != null;
    }
}
