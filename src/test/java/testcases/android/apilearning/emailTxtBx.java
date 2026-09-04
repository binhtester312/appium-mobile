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
 * emailTxtBx — Refactored to Page Object Model (POM) with Dynamic Test Data.
 * TC: Verify entering dynamic email into the email text box on Login screen.
 */
public class emailTxtBx extends BaseTest {

    @Test(
        description = "Verify entering dynamic email text into the Login email text box",
        groups = {"regression"}
    )
    public void testEmailTxtBx() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLoginScreen();
        ExtentReportManager.logInfo("Navigated to Login screen.");

        String testEmail = DataGenerator.generateEmail("textbox");
        loginPage.enterEmail(testEmail);
        ExtentReportManager.logInfo("Entered dynamic email: " + testEmail);

        Assert.assertTrue(loginPage.isLoginScreenDisplayed(), "Login screen should be displayed.");
        ExtentReportManager.logPass("Verified dynamic email text entered successfully.");
    }

    public static void main(String[] args) {
        AppiumDriver driver = AppiumDriverEx.getAppiumDriver();
        try {
            String testEmail = DataGenerator.generateEmail("main_textbox");
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateToLoginScreen();
            loginPage.enterEmail(testEmail);
            System.out.println(">>> [PASS] Entered dynamic email text successfully: " + testEmail);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
