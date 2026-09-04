package commons;

import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.ConfigReader;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ScreenshotUtil — Captures screenshots and saves them to disk.
 */
public class ScreenshotUtil {

    private static final Logger log = LoggerFactory.getLogger(ScreenshotUtil.class);
    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    // Prevent instantiation
    private ScreenshotUtil() {}

    /**
     * Takes a screenshot of the current screen and saves it to disk.
     *
     * @param driver   The AndroidDriver instance
     * @param testName The test name (used in filename)
     * @return Absolute path to the saved screenshot, or empty string on failure
     */
    public static String captureScreenshot(AppiumDriver driver, String testName) {
        try {
            // Generate timestamped filename
            String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
            String fileName = testName + "_" + timestamp + ".png";

            // Get the configured screenshot directory
            String screenshotDir = ConfigReader.getInstance()
                    .get("screenshot.path", "test-output/screenshots/");

            // Create directory if it doesn't exist
            File directory = new File(screenshotDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Take screenshot
            File tempScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Copy to our desired location
            File destFile = new File(screenshotDir + fileName);
            FileUtils.copyFile(tempScreenshot, destFile);

            String absolutePath = destFile.getAbsolutePath();
            log.info("📸 Screenshot saved: {}", absolutePath);
            return absolutePath;

        } catch (IOException e) {
            log.error("❌ Failed to capture screenshot: {}", e.getMessage());
            return "";
        } catch (Exception e) {
            log.error("❌ Unexpected error during screenshot: {}", e.getMessage());
            return "";
        }
    }
}
