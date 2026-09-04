package testcases.android.apilearning;

import commons.BaseTest;
import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.LoginPage;
import reports.ExtentReportManager;
import utils.AppiumDriverEx;

/**
 * clickOnElement — Refactored to Page Object Model (POM).
 * TC: Verify navigating to Login screen by tapping Login tab.
 */
public class clickOnElement extends BaseTest {

    @Test(
        description = "Verify clicking on Login tab opens Login screen",
        groups = {"smoke", "regression"}
    )
    public void testClickOnElement() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLoginScreen();
        ExtentReportManager.logInfo("Navigated to Login screen.");

        Assert.assertTrue(loginPage.isLoginScreenDisplayed(), "Login screen should be displayed.");
        ExtentReportManager.logPass("Verified Login screen is displayed successfully.");
    }

    public static void main(String[] args) {
        AppiumDriver driver = AppiumDriverEx.getAppiumDriver();
        try {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateToLoginScreen();
            System.out.println(">>> [PASS] Clicked on Login tab successfully!");
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
