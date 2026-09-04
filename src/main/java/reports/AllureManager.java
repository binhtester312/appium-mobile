package reports;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.Base64;

/**
 * AllureManager — Manages Allure Report attachments (Screenshots, Screen Recordings, Text Logs).
 */
public class AllureManager {

    private static final Logger log = LoggerFactory.getLogger(AllureManager.class);

    // Prevent instantiation
    private AllureManager() {}

    /**
     * Captures and attaches a PNG screenshot to the Allure Report.
     *
     * @param testName Name of the test method
     * @param driver   Active AppiumDriver instance
     * @return Screenshot bytes for Allure attachment
     */
    @Attachment(value = "📸 Screenshot: {testName}", type = "image/png")
    public static byte[] saveScreenshot(String testName, AppiumDriver driver) {
        if (driver == null) {
            log.warn("Cannot capture screenshot for Allure: driver is null.");
            return new byte[0];
        }
        try {
            byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("📸 Screenshot: " + testName, "image/png", new ByteArrayInputStream(screenshotBytes), "png");
            log.info("Attached screenshot to Allure for: {}", testName);
            return screenshotBytes;
        } catch (Exception e) {
            log.error("Failed to capture screenshot for Allure: {}", e.getMessage());
            return new byte[0];
        }
    }

    /**
     * Attaches raw MP4 video bytes to the Allure Report.
     *
     * @param videoTitle Title for the attachment
     * @param videoBytes Raw video byte array
     * @return Video bytes for Allure attachment
     */
    @Attachment(value = "🎥 Video Recording: {videoTitle}", type = "video/mp4")
    public static byte[] saveVideo(String videoTitle, byte[] videoBytes) {
        if (videoBytes == null || videoBytes.length == 0) {
            log.warn("Cannot attach empty video bytes to Allure.");
            return new byte[0];
        }
        try {
            Allure.addAttachment("🎥 Video Recording: " + videoTitle, "video/mp4", new ByteArrayInputStream(videoBytes), "mp4");
            log.info("Attached screen recording video to Allure: {} ({} bytes)", videoTitle, videoBytes.length);
            return videoBytes;
        } catch (Exception e) {
            log.error("Failed to attach video to Allure: {}", e.getMessage());
            return videoBytes;
        }
    }

    /**
     * Decodes Base64 video string (from Appium stopRecordingScreen) and attaches to Allure.
     *
     * @param videoTitle      Title for the attachment
     * @param base64VideoData Base64 encoded video data from Appium
     * @return Decoded video bytes for Allure attachment
     */
    public static byte[] saveBase64Video(String videoTitle, String base64VideoData) {
        if (base64VideoData == null || base64VideoData.isEmpty()) {
            log.warn("Cannot attach empty base64 video string to Allure.");
            return new byte[0];
        }
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64VideoData);
            return saveVideo(videoTitle, decodedBytes);
        } catch (Exception e) {
            log.error("Failed to decode and attach base64 video to Allure: {}", e.getMessage());
            return new byte[0];
        }
    }

    /**
     * Attaches a plain text log message to the Allure Report.
     *
     * @param logTitle Title of the text log
     * @param message  Content of the log
     * @return Log content for Allure attachment
     */
    @Attachment(value = "{logTitle}", type = "text/plain")
    public static String saveTextLog(String logTitle, String message) {
        try {
            Allure.addAttachment(logTitle, "text/plain", message);
        } catch (Exception ignored) {}
        return message;
    }
}
