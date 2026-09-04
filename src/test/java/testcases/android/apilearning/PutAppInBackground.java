package testcases.android.apilearning;

import commons.BaseTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.LoginPage;
import reports.ExtentReportManager;
import utils.AppiumDriverEx;
import utils.DataGenerator;
import utils.DeviceHelper;

import java.time.Duration;

/**
 * PutAppInBackground — Refactored to Page Object Model (POM) with Dynamic Data.
 * TC: Put app into background, modify Wi-Fi settings, return to app and dismiss dialog.
 */
public class PutAppInBackground extends BaseTest {

    @Test(description = "Verify putting app in background, interacting with device settings, and resuming app")
    public void testPutAppInBackground() {
        AndroidDriver androidDriver = (AndroidDriver) getDriver();

        // 1. Perform login with dynamic data
        LoginPage loginPage = new LoginPage(androidDriver);
        String dynamicEmail = DataGenerator.generateEmail("background");
        String dynamicPassword = DataGenerator.generatePassword();

        loginPage.navigateToLoginScreen()
                .login(dynamicEmail, dynamicPassword);
        ExtentReportManager.logInfo("Submitted login credentials for: " + dynamicEmail);

        // 2. Put app in background indefinitely
        DeviceHelper.putAppInBackground(androidDriver, Duration.ofSeconds(-1));
        ExtentReportManager.logInfo("App sent to background.");

        // 3. Open Wi-Fi settings and toggle
        DeviceHelper.openWifiSettings(androidDriver);
        ExtentReportManager.logInfo("Opened Wi-Fi settings.");

        DeviceHelper.toggleWifi(androidDriver, false);
        DeviceHelper.toggleWifi(androidDriver, true);
        ExtentReportManager.logInfo("Toggled Wi-Fi state.");

        // 4. Reactivate WDIO Demo App
        DeviceHelper.activateApp(androidDriver, "com.wdiodemoapp");
        ExtentReportManager.logInfo("Re-activated WDIO app.");

        // 5. Dismiss alert dialog by clicking OK
        loginPage.clickAlertOkButton();
        ExtentReportManager.logInfo("Dismissed success alert dialog by clicking OK.");

        Assert.assertTrue(loginPage.isLoginScreenDisplayed(), "Login screen should be visible after alert is dismissed.");
        ExtentReportManager.logPass("Verified backgrounding, device settings interaction, and resume flow.");
    }

    public static void main(String[] args) {
        AppiumDriver driver = AppiumDriverEx.getAppiumDriver();
        AndroidDriver androidDriver = (AndroidDriver) driver;
        try {
            LoginPage loginPage = new LoginPage(androidDriver);
            String email = DataGenerator.generateEmail("main_bg");
            String password = DataGenerator.generatePassword();

            loginPage.navigateToLoginScreen()
                    .login(email, password);

            DeviceHelper.putAppInBackground(androidDriver, Duration.ofSeconds(-1));
            DeviceHelper.openWifiSettings(androidDriver);
            DeviceHelper.toggleWifi(androidDriver, false);
            DeviceHelper.toggleWifi(androidDriver, true);

            DeviceHelper.activateApp(androidDriver, "com.wdiodemoapp");
            loginPage.clickAlertOkButton();

            System.out.println(">>> [PASS] Put app in background and resumed successfully with Dynamic Data!");
        } finally {
            if (androidDriver != null) {
                androidDriver.quit();
            }
        }
    }
}
