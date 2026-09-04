package testcases.android.apilearning;

import commons.BaseTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.LoginPage;
import reports.ExtentReportManager;
import utils.AppiumDriverEx;
import utils.DeviceHelper;

import java.io.File;

/**
 * RecordVideo — Refactored to Page Object Model (POM).
 * TC: Record screen video while executing Login test flow and save to /videos.
 */
public class RecordVideo extends BaseTest {

    @Test(description = "Verify screen recording during test execution")
    public void testRecordVideo() {
        AndroidDriver androidDriver = (AndroidDriver) getDriver();

        // 1. Start video recording
        DeviceHelper.startRecording(androidDriver);
        ExtentReportManager.logInfo("Started screen recording.");

        String videoPath = null;
        try {
            // 2. Perform Login Page interactions
            LoginPage loginPage = new LoginPage(androidDriver);
            loginPage.navigateToLoginScreen();
            ExtentReportManager.logInfo("Navigated to Login screen.");

            loginPage.login("test@maildomain.com", "password");
            ExtentReportManager.logInfo("Submitted login credentials.");

            Assert.assertTrue(loginPage.isAlertDisplayed(), "Success/Alert dialog should be displayed.");

        } finally {
            // 3. Stop recording and save video
            videoPath = DeviceHelper.stopRecordingAndSave(androidDriver, "testRecordVideo");
            ExtentReportManager.logInfo("Saved test video to: " + videoPath);
        }

        Assert.assertNotNull(videoPath, "Video path should not be null.");
        Assert.assertTrue(new File(videoPath).exists(), "Video file should exist on disk.");
        ExtentReportManager.logPass("Verified screen recording completed and saved successfully.");
    }

    public static void main(String[] args) {
        AppiumDriver driver = AppiumDriverEx.getAppiumDriver();
        AndroidDriver androidDriver = (AndroidDriver) driver;
        try {
            DeviceHelper.startRecording(androidDriver);
            System.out.println(">>> [RECORD] Bắt đầu ghi màn hình...");

            LoginPage loginPage = new LoginPage(androidDriver);
            loginPage.navigateToLoginScreen();
            loginPage.login("test@maildomain.com", "password");

            String videoPath = DeviceHelper.stopRecordingAndSave(androidDriver, "mainRecordVideo");
            System.out.println(">>> [RECORD] Video đã lưu tại: " + videoPath);
            System.out.println(">>> [PASS] Recorded video successfully!");
        } finally {
            if (androidDriver != null) {
                androidDriver.quit();
            }
        }
    }
}
