package testcases.android.apilearning;

import commons.BaseTest;
import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.LoginPage;
import reports.ExtentReportManager;
import utils.AppiumDriverEx;

/**
 * setValues — Refactored to Page Object Model (POM) with Fluent Method Chaining.
 * TC: Input email and password, then submit login form.
 */
public class setValues extends BaseTest {

    @Test(description = "Verify entering email and password and submitting login form using method chaining")
    public void testSetValues() {
        LoginPage loginPage = new LoginPage(getDriver());

        // Fluent Method Chaining: Navigate -> Enter Credentials -> Submit -> Check Alert State
        boolean isAlertShown = loginPage
                .navigateToLoginScreen()
                .enterEmail("nhubinh@maildomain.com")
                .enterPassword("12345678")
                .tapLoginButton()
                .isAlertDisplayed();

        ExtentReportManager.logInfo("Alert dialog displayed state: " + isAlertShown);

        Assert.assertTrue(isAlertShown, "Success/Alert dialog should appear after login submission.");
        loginPage.clickAlertOkButton();
        ExtentReportManager.logPass("Verified login form submitted successfully using Method Chaining.");
    }

    public static void main(String[] args) {
        AppiumDriver driver = AppiumDriverEx.getAppiumDriver();
        try {
            boolean isAlert = new LoginPage(driver)
                    .navigateToLoginScreen()
                    .enterEmail("nhubinh@maildomain.com")
                    .enterPassword("12345678")
                    .tapLoginButton()
                    .isAlertDisplayed();

            System.out.println("Alert displayed: " + isAlert);
            System.out.println(">>> [PASS] Set values and submitted login successfully with Method Chaining!");
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
