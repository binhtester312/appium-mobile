package driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.ConfigReader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * DriverFactory — Creates and manages AppiumDriver instances for both Android & iOS.
 *
 * DESIGN PATTERN: ThreadLocal Pattern
 * WHY: Enables safe parallel test execution across multiple threads/devices.
 */
public class DriverFactory {

    private static final Logger log = LoggerFactory.getLogger(DriverFactory.class);

    /**
     * ThreadLocal holds the active AppiumDriver (AndroidDriver or IOSDriver) for each thread.
     */
    private static final ThreadLocal<AppiumDriver> driverThreadLocal = new ThreadLocal<>();

    // Prevent instantiation
    private DriverFactory() {}

    /**
     * Initializes a new AppiumDriver (Android or iOS) for the current thread.
     * Platform is determined by 'target.platform' in config.properties or System property.
     */
    public static void initDriver() {
        if (driverThreadLocal.get() != null) {
            log.warn("Driver already exists for this thread. Quitting existing driver first.");
            quitDriver();
        }

        ConfigReader config = ConfigReader.getInstance();
        
        // System property takes precedence over config.properties (e.g. mvn test -Dtarget.platform=ios)
        String platform = System.getProperty("target.platform", config.get("target.platform", "android")).toLowerCase();

        log.info("🚀 Initializing Appium Driver for Platform: [{}]", platform.toUpperCase());

        try {
            URL appiumServerUrl = AppiumServerManager.getServerUrl();
            AppiumDriver driver;

            if ("ios".equals(platform)) {
                driver = createIOSDriver(appiumServerUrl, config);
            } else {
                driver = createAndroidDriver(appiumServerUrl, config);
            }

            // Set implicit wait
            driver.manage().timeouts().implicitlyWait(
                    Duration.ofSeconds(config.getInt("implicit.wait"))
            );

            driverThreadLocal.set(driver);
            log.info("✅ {} initialized successfully.", driver.getClass().getSimpleName());

        } catch (Exception e) {
            log.error("❌ Failed to initialize Appium Driver for platform [{}]: {}", platform, e.getMessage());
            throw new RuntimeException("Appium Driver initialization failed. " +
                    "Is Appium server running? Is the emulator/simulator started?", e);
        }
    }

    /**
     * Creates an AndroidDriver with UiAutomator2Options.
     */
    private static AndroidDriver createAndroidDriver(URL serverUrl, ConfigReader config) {
        UiAutomator2Options options = new UiAutomator2Options();

        options.setDeviceName(config.get("android.device.name", config.get("device.name", "Pixel_8_API_35")));
        options.setUdid(config.get("android.udid", config.get("udid", "emulator-5554")));
        options.setPlatformVersion(config.get("android.platform.version", config.get("platform.version", "15.0")));
        options.setAutomationName(config.get("android.automation.name", "UiAutomator2"));

        String appPath = config.get("android.app.path", config.get("app.path", ""));
        if (!appPath.isEmpty()) {
            File appFile = new File(appPath);
            options.setApp(appFile.getAbsolutePath());
            log.info("Android App File: {}", appFile.getAbsolutePath());
        } else {
            options.setAppPackage(config.get("android.app.package"));
            options.setAppActivity(config.get("android.app.activity"));
        }

        String appWaitActivity = config.get("android.app.wait.activity", "*");
        options.setAppWaitActivity(appWaitActivity);
        options.setNoReset(true);
        options.setAutoGrantPermissions(true);
        options.setNewCommandTimeout(Duration.ofSeconds(config.getInt("implicit.wait") * 6L));

        log.info("Starting AndroidDriver on device: {}", options.getDeviceName());
        return new AndroidDriver(serverUrl, options);
    }

    /**
     * Creates an IOSDriver with XCUITestOptions.
     */
    private static IOSDriver createIOSDriver(URL serverUrl, ConfigReader config) {
        XCUITestOptions options = new XCUITestOptions();

        options.setDeviceName(config.get("ios.device.name", "iPhone 16 Pro"));
        options.setPlatformVersion(config.get("ios.platform.version", "18.0"));
        options.setAutomationName(config.get("ios.automation.name", "XCUITest"));

        String udid = config.get("ios.udid", "");
        if (!udid.isEmpty()) {
            options.setUdid(udid);
        }

        String appPath = config.get("ios.app.path", "");
        if (!appPath.isEmpty()) {
            File appFile = new File(appPath);
            options.setApp(appFile.getAbsolutePath());
            log.info("iOS App File: {}", appFile.getAbsolutePath());
        } else {
            String bundleId = config.get("ios.bundle.id", "");
            if (!bundleId.isEmpty()) {
                options.setBundleId(bundleId);
            }
        }

        options.setNoReset(true);
        options.setNewCommandTimeout(Duration.ofSeconds(config.getInt("implicit.wait") * 6L));

        log.info("Starting IOSDriver on device: {} ({})", options.getDeviceName(), options.getPlatformVersion());
        return new IOSDriver(serverUrl, options);
    }

    /**
     * Returns the AppiumDriver for the current thread.
     *
     * @return AppiumDriver instance for this thread
     */
    public static AppiumDriver getDriver() {
        AppiumDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new IllegalStateException(
                    "❌ Driver is null! Call DriverFactory.initDriver() in @BeforeMethod first."
            );
        }
        return driver;
    }

    /**
     * Quits the AppiumDriver and removes it from ThreadLocal.
     */
    public static void quitDriver() {
        AppiumDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
                log.info("✅ AppiumDriver quit successfully.");
            } catch (Exception e) {
                log.warn("⚠️ Error while quitting driver: {}", e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }

    /**
     * Checks if driver is currently active for this thread.
     */
    public static boolean isDriverActive() {
        return driverThreadLocal.get() != null;
    }
}
