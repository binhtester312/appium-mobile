package testcases.android.apilearning;

import commons.BaseTest;
import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.LoginPage;
import reports.ExtentReportManager;
import utils.AppiumDriverEx;

/**
 * GetValue — Refactored to Page Object Model (POM).
 * TC: Verify getting text value from success alert dialog.
 */
public class GetValue extends BaseTest {

    @Test(description = "Verify retrieving text value from alert dialog after login")
    public void testGetValue() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLoginScreen();
        ExtentReportManager.logInfo("Navigated to Login screen.");

        loginPage.login("tuhuynh@maildomain.com", "password");
        ExtentReportManager.logInfo("Submitted login credentials.");

        String title = loginPage.getAlertTitle();
        ExtentReportManager.logInfo("Retrieved alert title: " + title);

        Assert.assertEquals(title, "Success", "Alert title should be 'Success'.");
        loginPage.clickAlertOkButton();
        ExtentReportManager.logPass("Verified dialog title is 'Success'.");
    }

    public static void main(String[] args) {
        AppiumDriver driver = AppiumDriverEx.getAppiumDriver();
        try {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateToLoginScreen();
            loginPage.login("tuhuynh@maildomain.com", "password");
            String title = loginPage.getAlertTitle();
            System.out.println("Dialog Title: " + title);
            System.out.println(">>> [PASS] Retrieved value successfully!");
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
