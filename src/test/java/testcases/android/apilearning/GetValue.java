package testcases.android.apilearning;

import commons.BaseTest;
import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.LoginPage;
import reports.ExtentReportManager;
import utils.AppiumDriverEx;
import utils.DataGenerator;

/**
 * GetValue — Refactored to Page Object Model (POM) with Fluent Method Chaining & Dynamic Data.
 * TC: Verify getting text value from success alert dialog.
 */
public class GetValue extends BaseTest {

    @Test(description = "Verify retrieving text value from alert dialog after login using dynamic data")
    public void testGetValue() {
        LoginPage loginPage = new LoginPage(getDriver());

        String dynamicEmail = DataGenerator.generateEmail("getvalue");
        String dynamicPassword = DataGenerator.generatePassword();

        // Fluent Method Chaining: Navigate -> Enter Dynamic Credentials -> Submit -> Retrieve Alert Title
        String title = loginPage
                .navigateToLoginScreen()
                .enterEmail(dynamicEmail)
                .enterPassword(dynamicPassword)
                .tapLoginButton()
                .getAlertTitle();

        ExtentReportManager.logInfo("Generated email: " + dynamicEmail);
        ExtentReportManager.logInfo("Retrieved alert title: " + title);

        Assert.assertEquals(title, "Success", "Alert title should be 'Success'.");
        loginPage.clickAlertOkButton();
        ExtentReportManager.logPass("Verified dialog title is 'Success' using Dynamic Data & Method Chaining.");
    }

    public static void main(String[] args) {
        AppiumDriver driver = AppiumDriverEx.getAppiumDriver();
        try {
            String email = DataGenerator.generateEmail("main_getvalue");
            String password = DataGenerator.generatePassword();

            String title = new LoginPage(driver)
                    .navigateToLoginScreen()
                    .enterEmail(email)
                    .enterPassword(password)
                    .tapLoginButton()
                    .getAlertTitle();

            System.out.println("Dialog Title: " + title);
            System.out.println(">>> [PASS] Retrieved value successfully with Dynamic Data & Method Chaining!");
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
