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

import java.time.Duration;

/**
 * PutAppInBackground — Verifies app backgrounding and foreground resumption.
 */
@Epic("API Learning")
@Feature("Device App Lifecycle & Settings")
public class PutAppInBackground extends BaseTest {

    @Test(
        description = "TC_BG_001: Verify putting app in background, interacting with device settings, and resuming app",
        groups = {"regression"}
    )
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify app lifecycle: submit login, send app to background, modify device Wi-Fi settings, reactivate app, and dismiss dialog.")
    public void testPutAppInBackground() {
        AndroidDriver androidDriver = (AndroidDriver) getDriver();

        // 1. Perform login with dynamic data
        LoginPage loginPage = new LoginPage(androidDriver);
        String dynamicEmail = DataGenerator.generateEmail("background");
        String dynamicPassword = DataGenerator.generatePassword();

        loginPage.navigateToLoginScreen()
                .login(dynamicEmail, dynamicPassword);

        // 2. Put app in background indefinitely
        DeviceHelper.putAppInBackground(androidDriver, Duration.ofSeconds(-1));

        // 3. Open Wi-Fi settings and toggle
        DeviceHelper.openWifiSettings(androidDriver);
        DeviceHelper.toggleWifi(androidDriver, false);
        DeviceHelper.toggleWifi(androidDriver, true);

        // 4. Reactivate WDIO Demo App
        DeviceHelper.activateApp(androidDriver, "com.wdiodemoapp");

        // 5. Dismiss alert dialog by clicking OK
        loginPage.clickAlertOkButton();

        Assert.assertTrue(loginPage.isLoginScreenDisplayed(), "Login screen should be visible after alert is dismissed.");
    }
}
