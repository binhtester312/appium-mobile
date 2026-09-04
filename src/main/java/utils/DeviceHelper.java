package utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.android.AndroidStopScreenRecordingOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Base64;
import java.util.Map;

/**
 * DeviceHelper — Utilities for device-level mobile actions (Wi-Fi, Screen recording, App backgrounding).
 */
public class DeviceHelper {

    private static final Logger log = LoggerFactory.getLogger(DeviceHelper.class);

    /**
     * Puts the app in background for a specified duration.
     * Use Duration.ofSeconds(-1) to put it in background indefinitely.
     */
    public static void putAppInBackground(AndroidDriver driver, Duration duration) {
        log.info("Putting app into background for: {}", duration);
        driver.runAppInBackground(duration);
    }

    /**
     * Activates / brings an app back to the foreground by package name.
     */
    public static void activateApp(AndroidDriver driver, String appPackage) {
        log.info("Activating app package: {}", appPackage);
        driver.activateApp(appPackage);
    }

    /**
     * Opens Android Wi-Fi settings screen directly.
     */
    public static void openWifiSettings(AndroidDriver driver) {
        log.info("Opening Android Wi-Fi settings.");
        driver.executeScript("mobile: startActivity", Map.of(
                "action", "android.settings.WIFI_SETTINGS"
        ));
    }

    /**
     * Toggles Wi-Fi switch in Android Settings.
     */
    public static void toggleWifi(AndroidDriver driver, boolean targetState) {
        By wifiSwitchSelector = AppiumBy.id("com.android.settings:id/switchWidget");
        try {
            WebElement wifiSwitch = driver.findElement(wifiSwitchSelector);
            boolean isWifiOn = Boolean.parseBoolean(wifiSwitch.getAttribute("checked"));
            log.info("Current Wi-Fi state: {}", isWifiOn ? "ON" : "OFF");

            if (isWifiOn != targetState) {
                wifiSwitch.click();
                log.info("Wi-Fi switched to: {}", targetState ? "ON" : "OFF");
            }
        } catch (Exception e) {
            log.warn("Could not toggle Wi-Fi switch: {}", e.getMessage());
        }
    }

    /**
     * Starts video screen recording on the Android device.
     */
    public static void startRecording(AndroidDriver driver) {
        log.info("Starting screen recording...");
        driver.startRecordingScreen(
                new AndroidStartScreenRecordingOptions()
                        .withBitRate(4_000_000)
                        .withTimeLimit(Duration.ofSeconds(180))
        );
    }

    /**
     * Stops video screen recording and saves the .mp4 file to /videos.
     *
     * @return Absolute path of the saved video file
     */
    public static String stopRecordingAndSave(AndroidDriver driver, String filePrefix) {
        log.info("Stopping screen recording and saving video...");
        try {
            String videoBase64 = driver.stopRecordingScreen(new AndroidStopScreenRecordingOptions());
            byte[] decodedVideo = Base64.getDecoder().decode(videoBase64);

            Path videosDir = Paths.get(System.getProperty("user.dir"), "videos");
            Files.createDirectories(videosDir);

            String fileName = String.format("%s-%d.mp4", filePrefix, System.currentTimeMillis());
            File videoFile = videosDir.resolve(fileName).toFile();

            try (FileOutputStream fos = new FileOutputStream(videoFile)) {
                fos.write(decodedVideo);
            }

            log.info("Video saved successfully at: {}", videoFile.getAbsolutePath());
            return videoFile.getAbsolutePath();
        } catch (Exception e) {
            log.error("Failed to save screen recording: {}", e.getMessage());
            throw new RuntimeException("Screen recording save failed: " + e.getMessage(), e);
        }
    }
}
