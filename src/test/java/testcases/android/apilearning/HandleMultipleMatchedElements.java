package testcases.android.apilearning;

import commons.BaseTest;
import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.LoginPage;
import reports.ExtentReportManager;
import utils.AppiumDriverEx;

/**
 * HandleMultipleMatchedElements — Refactored to Page Object Model (POM).
 * TC: Verify handling and interacting with multiple elements matching the same locator.
 */
public class HandleMultipleMatchedElements extends BaseTest {

    @Test(description = "Verify handling multiple matched elements with text 'Login'")
    public void testHandleMultipleMatchedElements() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLoginScreen();
        ExtentReportManager.logInfo("Navigated to Login screen.");

        int count = loginPage.getLoginTextElementsCount();
        ExtentReportManager.logInfo("Matched elements count: " + count);

        Assert.assertTrue(count >= 2, "There should be at least 2 elements with text 'Login'.");

        loginPage.clickLoginTextElementAt(0);
        ExtentReportManager.logPass("Successfully clicked first Login text element.");
    }

    public static void main(String[] args) {
        AppiumDriver driver = AppiumDriverEx.getAppiumDriver();
        try {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateToLoginScreen();
            int count = loginPage.getLoginTextElementsCount();
            System.out.println("How many elements matched: " + count);
            loginPage.clickLoginTextElementAt(0);
            System.out.println(">>> [PASS] Handled multiple matched elements successfully!");
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
