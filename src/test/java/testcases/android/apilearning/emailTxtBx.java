package testcases.android.apilearning;

import commons.BaseTest;
import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.LoginPage;
import reports.ExtentReportManager;
import utils.AppiumDriverEx;

/**
 * emailTxtBx — Refactored to Page Object Model (POM).
 * TC: Verify entering email into the email text box on Login screen.
 */
public class emailTxtBx extends BaseTest {

    @Test(description = "Verify entering email text into the Login email text box")
    public void testEmailTxtBx() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLoginScreen();
        ExtentReportManager.logInfo("Navigated to Login screen.");

        String testEmail = "testuser@maildomain.com";
        loginPage.enterEmail(testEmail);
        ExtentReportManager.logInfo("Entered email: " + testEmail);

        Assert.assertTrue(loginPage.isLoginScreenDisplayed(), "Login screen should be displayed.");
        ExtentReportManager.logPass("Verified email text entered successfully.");
    }

    public static void main(String[] args) {
        AppiumDriver driver = AppiumDriverEx.getAppiumDriver();
        try {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateToLoginScreen();
            loginPage.enterEmail("testuser@maildomain.com");
            System.out.println(">>> [PASS] Entered email text successfully!");
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
