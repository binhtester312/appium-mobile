package testcases.android.apilearning;

import commons.BaseTest;
import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.LoginPage;
import reports.ExtentReportManager;
import utils.AppiumDriverEx;

/**
 * setValues — Refactored to Page Object Model (POM).
 * TC: Input email and password, then submit login form.
 */
public class setValues extends BaseTest {

    @Test(description = "Verify entering email and password and submitting login form")
    public void testSetValues() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLoginScreen();
        ExtentReportManager.logInfo("Navigated to Login screen.");

        loginPage.login("nhubinh@maildomain.com", "12345678");
        ExtentReportManager.logInfo("Entered credentials and clicked Login button.");

        Assert.assertTrue(loginPage.isAlertDisplayed(), "Success/Alert dialog should appear after login submission.");
        loginPage.clickAlertOkButton();
        ExtentReportManager.logPass("Verified login form submitted successfully.");
    }

    public static void main(String[] args) {
        AppiumDriver driver = AppiumDriverEx.getAppiumDriver();
        try {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateToLoginScreen();
            loginPage.login("nhubinh@maildomain.com", "12345678");
            System.out.println(">>> [PASS] Set values and submitted login successfully!");
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
