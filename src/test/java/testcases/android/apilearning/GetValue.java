package testcases.android.apilearning;

import commons.BaseTest;
import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.LoginPage;
import reports.ExtentReportManager;
import utils.AppiumDriverEx;

/**
 * GetValue — Refactored to Page Object Model (POM) with Fluent Method Chaining.
 * TC: Verify getting text value from success alert dialog.
 */
public class GetValue extends BaseTest {

    @Test(description = "Verify retrieving text value from alert dialog after login using method chaining")
    public void testGetValue() {
        LoginPage loginPage = new LoginPage(getDriver());

        // Fluent Method Chaining: Navigate -> Enter Credentials -> Submit -> Retrieve Alert Title
        String title = loginPage
                .navigateToLoginScreen()
                .enterEmail("tuhuynh@maildomain.com")
                .enterPassword("password")
                .tapLoginButton()
                .getAlertTitle();

        ExtentReportManager.logInfo("Retrieved alert title: " + title);

        Assert.assertEquals(title, "Success", "Alert title should be 'Success'.");
        loginPage.clickAlertOkButton();
        ExtentReportManager.logPass("Verified dialog title is 'Success' using Method Chaining.");
    }

    public static void main(String[] args) {
        AppiumDriver driver = AppiumDriverEx.getAppiumDriver();
        try {
            String title = new LoginPage(driver)
                    .navigateToLoginScreen()
                    .enterEmail("tuhuynh@maildomain.com")
                    .enterPassword("password")
                    .tapLoginButton()
                    .getAlertTitle();

            System.out.println("Dialog Title: " + title);
            System.out.println(">>> [PASS] Retrieved value successfully with Method Chaining!");
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
