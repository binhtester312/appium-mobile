package testcases.android.apilearning;

import commons.BaseTest;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.LoginPage;
import utils.DataGenerator;
import utils.DeviceHelper;

import java.io.File;

/**
 * RecordVideo — Records screen video during test execution and attaches to Allure Report.
 */
@Epic("API Learning")
@Feature("Screen Recording")
public class RecordVideo extends BaseTest {

    @Test(
        description = "TC_RECORD_001: Verify screen recording during test execution",
        groups = {"regression"}
    )
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that screen recording captures test execution on device, saves the MP4 file to disk, and attaches it into Allure Report.")
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
}
