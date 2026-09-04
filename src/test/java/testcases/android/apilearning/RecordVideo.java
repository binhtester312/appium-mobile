package testcases.android.apilearning;

import commons.BaseTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.LoginPage;
import utils.AppiumDriverEx;
import utils.DataGenerator;
import utils.DeviceHelper;

import java.io.File;

/**
 * RecordVideo — Refactored to Page Object Model (POM) with Dynamic Data and Allure Video Attachment.
 * TC: Record screen video while executing Login test flow and attach to Allure.
 */
@Epic("API Learning")
@Feature("Screen Recording")
public class RecordVideo extends BaseTest {

    @Test(
        description = "Verify screen recording during test execution",
        groups = {"regression"}
    )
    @Severity(SeverityLevel.NORMAL)
    public void testRecordVideo() {
        AndroidDriver androidDriver = (AndroidDriver) getDriver();

        // 1. Start video recording
        DeviceHelper.startRecording(androidDriver);

        String videoPath = null;
        try {
            // 2. Perform Login Page interactions with Dynamic Data
            LoginPage loginPage = new LoginPage(androidDriver);
            String dynamicEmail = DataGenerator.generateEmail("video");
            String dynamicPassword = DataGenerator.generatePassword();

            loginPage.navigateToLoginScreen()
                    .login(dynamicEmail, dynamicPassword);

            Assert.assertTrue(loginPage.isAlertDisplayed(), "Success/Alert dialog should be displayed.");
            loginPage.clickAlertOkButton();

        } finally {
            // 3. Stop recording, save video to disk, and automatically attach to Allure Report
            videoPath = DeviceHelper.stopRecordingAndSave(androidDriver, "testRecordVideo");
        }

        Assert.assertNotNull(videoPath, "Video path should not be null.");
        Assert.assertTrue(new File(videoPath).exists(), "Video file should exist on disk.");
    }

    public static void main(String[] args) {
        AppiumDriver driver = AppiumDriverEx.getAppiumDriver();
        AndroidDriver androidDriver = (AndroidDriver) driver;
        try {
            DeviceHelper.startRecording(androidDriver);
            System.out.println(">>> [RECORD] Bắt đầu ghi màn hình...");

            LoginPage loginPage = new LoginPage(androidDriver);
            String email = DataGenerator.generateEmail("main_video");
            String password = DataGenerator.generatePassword();

            loginPage.navigateToLoginScreen().login(email, password).clickAlertOkButton();

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
